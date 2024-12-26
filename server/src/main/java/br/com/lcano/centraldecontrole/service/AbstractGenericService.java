package br.com.lcano.centraldecontrole.service;

import br.com.lcano.centraldecontrole.dto.BaseDTO;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGenericService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    protected abstract <D extends BaseDTO<T>> D getDtoInstance();

    private String getIdFieldName(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null;
    }

    public List<T> findAll() {
        String idField = getIdFieldName(getEntityClass());
        if (idField != null) {
            return getRepository().findAll(Sort.by(Sort.Order.asc(idField)));
        }
        return getRepository().findAll();
    }

    public Page<T> findAllPaged(Pageable pageable) {
        String idField = getIdFieldName(getEntityClass());
        if (idField != null) {
            Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc(idField)));
            return getRepository().findAll(sortedPageable);
        }
        return getRepository().findAll(pageable);
    }

    public <D extends BaseDTO<T>> List<D> findAllAsDto() {
        return (List<D>) findAll().stream()
                .map(entity -> getDtoInstance().fromEntity(entity))
                .collect(Collectors.toList());
    }

    public <D extends BaseDTO<T>> Page<D> findAllPagedAsDto(Pageable pageable) {
        return (Page<D>) findAllPaged(pageable)
                .map(entity -> getDtoInstance().fromEntity(entity));
    }

    public <D extends BaseDTO<T>> D findByIdAsDto(ID id) {
        T entity = findById(id);
        return (D) getDtoInstance().fromEntity(entity);
    }

    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + id));
    }

    @Transactional
    public T save(T entity) {
        return getRepository().save(entity);
    }

    public <D extends BaseDTO<T>> D saveAsDto(D dto) {
        T entity = dto.toEntity();
        T savedEntity = save(entity);
        return (D) getDtoInstance().fromEntity(savedEntity);
    }

    @Transactional
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    private Class<?> getEntityClass() {
        return (Class<?>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
