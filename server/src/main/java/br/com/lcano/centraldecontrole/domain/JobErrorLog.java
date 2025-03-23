package br.com.lcano.centraldecontrole.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "joberrorlog")
@Entity
@Data
public class JobErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jobname", nullable = false)
    private String jobName;

    @Column(name = "errortimestamp", nullable = false)
    private LocalDateTime errorTimestamp;

    @Column(name = "errormessage", nullable = false, length = 500)
    private String errorMessage;

    @Column(name = "stacktrace", columnDefinition = "TEXT")
    private String stackTrace;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
}
