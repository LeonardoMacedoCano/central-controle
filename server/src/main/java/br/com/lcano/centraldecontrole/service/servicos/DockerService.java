package br.com.lcano.centraldecontrole.service.servicos;

import br.com.lcano.centraldecontrole.enums.servicos.ContainerActionEnum;
import br.com.lcano.centraldecontrole.enums.servicos.DockerStatusEnum;
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

    public DockerStatusEnum changeContainerStatusByName(String containerName, ContainerActionEnum action) {
        try {
            Container container = getContainerByName(containerName);
            return switch (action) {
                case START -> {
                    dockerClient.startContainerCmd(container.getId()).exec();
                    yield DockerStatusEnum.RUNNING;
                }
                case STOP -> {
                    dockerClient.stopContainerCmd(container.getId()).exec();
                    yield DockerStatusEnum.STOPPED;
                }
                case RESTART -> {
                    dockerClient.restartContainerCmd(container.getId()).exec();
                    yield DockerStatusEnum.RUNNING;
                }
            };
        } catch (NotFoundException e) {
            return DockerStatusEnum.NOT_FOUND;
        } catch (Exception e) {
            return DockerStatusEnum.ERROR;
        }
    }

    public DockerStatusEnum getContainerStatusByName(String containerName) {
        try {
            Container container = getContainerByName(containerName);
            return container.getState().equals("running") ? DockerStatusEnum.RUNNING : DockerStatusEnum.STOPPED;
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
                .orElseThrow(() -> new NotFoundException("Contêiner não encontrado: " + containerName));
    }
}
