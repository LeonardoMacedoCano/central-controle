package br.com.lcano.centraldecontrole.domain.servicos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "servidorconfig")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class ServidorConfig {
    @Id
    private Long id;

    @Column(name = "ipexterno", length = 50, nullable = false)
    private String ipExterno;
}
