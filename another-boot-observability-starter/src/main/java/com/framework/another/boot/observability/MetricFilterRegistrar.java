package com.framework.another.boot.observability;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.cloud.kubernetes.commons.PodUtils;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@AutoConfiguration
@ConditionalOnCloudPlatform(value = CloudPlatform.KUBERNETES)
public class MetricFilterRegistrar {

    @Bean
    public MeterFilter kubernetesLabelRegistrars (PodUtils<Pod> podPodUtils) {
        Pod currentPod = podPodUtils.currentPod().get();
        ObjectMeta podMeta = currentPod.getMetadata();
        Set<Tag> commonTagSet = new HashSet<>();
        commonTagSet.add(Tag.of("namespace", podMeta.getNamespace()));
        commonTagSet.add(Tag.of("podName", podMeta.getName()));
        return MeterFilter.commonTags(commonTagSet);
    }
}