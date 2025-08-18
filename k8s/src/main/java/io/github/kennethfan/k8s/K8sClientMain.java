package io.github.kennethfan.k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.CoreV1EndpointPort;
import io.kubernetes.client.openapi.models.V1EndpointAddress;
import io.kubernetes.client.openapi.models.V1EndpointSubset;
import io.kubernetes.client.openapi.models.V1Endpoints;
import io.kubernetes.client.util.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class K8sClientMain {

    public static void main(String[] args) {
        try {
            // 加载Kubernetes配置，通常是~/.kube/config
            ApiClient client = Config.fromConfig(System.getProperty("user.home") + "/.kube/config");
//            client = Config.defaultClient();
            CoreV1Api api = new CoreV1Api(client);

            // 替换为实际的服务名和命名空间
            String serviceName = System.getProperty("k8s.service");
            String namespace = System.getProperty("k8s.namespace");

            // 获取服务的端点
            V1Endpoints endpoints = api.readNamespacedEndpoints(serviceName, namespace, null);
            log.info("Endpoints: {}", endpoints);

            for (V1EndpointSubset subset : Objects.requireNonNull(endpoints.getSubsets())) {
                Integer port = subset.getPorts()
                        .stream()
                        .filter(o -> "http".equals(o.getName()))
                        .findAny()
                        .map(CoreV1EndpointPort::getPort)
                        .orElse(null);
                List<String> ips = subset.getAddresses()
                        .stream()
                        .map(V1EndpointAddress::getIp)
                        .toList();
                if (CollectionUtils.isEmpty(ips)) {
                    log.warn("No ips found for endpoint {}", endpoints.getMetadata().getName());
                    continue;
                }
                for (String ip : ips) {
                    log.info("Adding ip {}:{} to endpoint {}", ip, port, endpoints.getMetadata().getName());
                }
            }
        } catch (IOException | ApiException e) {
            e.printStackTrace();
        }
    }
}
