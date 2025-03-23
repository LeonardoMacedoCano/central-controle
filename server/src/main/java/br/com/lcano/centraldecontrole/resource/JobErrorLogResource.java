package br.com.lcano.centraldecontrole.resource;

import br.com.lcano.centraldecontrole.dto.JobErrorLogDTO;
import br.com.lcano.centraldecontrole.service.JobErrorLogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/job-error-log")
public class JobErrorLogResource {
    @Autowired
    private JobErrorLogService service;

    @GetMapping("/{id}")
    public ResponseEntity<JobErrorLogDTO> findByIdAsDto(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findByIdAsDto(id));
    }
}