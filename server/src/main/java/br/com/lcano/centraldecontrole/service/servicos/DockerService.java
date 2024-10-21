package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatusEnum;
import br.com.lcano.centraldecontrole.exception.servicos.DockerException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DockerService {
    private final DockerClient dockerClient;

    public DockerService(@Value("${docker.socket.uri:unix:///var/run/docker.sock}") String dockerSocketUri) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerSocketUri)
                .build();

        this.dockerClient = DockerClientBuilder.getInstance(config).build();
    }

    public void changeContainerStatusByName(String containerName, ContainerActionEnum action) {
        final Container container = getContainerByName(containerName);

        try {
            switch (action) {
                case START -> dockerClient.startContainerCmd(container.getId()).exec();
                case STOP -> dockerClient.stopContainerCmd(container.getId()).exec();
                case RESTART -> dockerClient.restartContainerCmd(container.getId()).exec();
                default -> throw new IllegalArgumentException("Ação inválida: " + action);
            }
        } catch (NotFoundException e) {
            throw new DockerException.DockerNaoEncontradoByName(containerName);
        } catch (Exception e) {
            throw new DockerException.DockerErroAlterarStatus(containerName, e.getMessage());
        }
    }

    public DockerStatusEnum getContainerStatusByName(String containerName) {
        try {
            Container container = getContainerByName(containerName);
            String state = container.getState().toLowerCase();

            return switch (state) {
                case "running" -> DockerStatusEnum.RUNNING;
                case "exited", "stopped" -> DockerStatusEnum.STOPPED;
                case "paused" -> DockerStatusEnum.PAUSED;
                case "restarting" -> DockerStatusEnum.RESTARTING;
                case "dead" -> DockerStatusEnum.DEAD;
                default -> DockerStatusEnum.UNKNOWN;
            };
        } catch (NotFoundException e) {
            return DockerStatusEnum.NOT_FOUND;
        } catch (Exception e) {
            return DockerStatusEnum.ERROR;
        }
    }

    private Container getContainerByName(String containerName) {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        return containers.stream()
                .filter(container -> container.getNames()[0].equals("/" + containerName))
                .findFirst()
                .orElseThrow(() -> new DockerException.DockerNaoEncontradoByName(containerName));
    }
}
