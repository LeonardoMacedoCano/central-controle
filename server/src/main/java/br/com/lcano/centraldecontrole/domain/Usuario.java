package br.com.lcano.centraldecontrole.domain;

import br.com.lcano.centraldecontrole.util.BooleanToCharConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "usuario")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String senha;

    @Column(name = "datainclusao", nullable = false)
    private Date dataInclusao;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "idtema", referencedColumnName = "id")
    private Tema tema;

    @OneToOne
    @JoinColumn(name = "idarquivo", referencedColumnName = "id")
    private Arquivo arquivo;

    public Usuario(String username, String senha, Date dataInclusao) {
        this.username = username;
        this.senha = senha;
        this.dataInclusao = dataInclusao;
        this.ativo = true;
    }

    public Usuario(Long id, String username, String senha, Date dataInclusao, Boolean ativo) {
        this.id = id;
        this.username = username;
        this.senha = senha;
        this.dataInclusao = dataInclusao;
        this.ativo = ativo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
