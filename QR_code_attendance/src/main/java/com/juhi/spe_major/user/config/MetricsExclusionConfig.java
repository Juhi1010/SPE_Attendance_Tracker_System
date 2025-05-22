package com.juhi.spe_major.user.config;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.config.MeterFilter;

@Configuration
@EnableAutoConfiguration(exclude = {
        SystemMetricsAutoConfiguration.class,
        SimpleMetricsExportAutoConfiguration.class
})
public class MetricsExclusionConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .meterFilter(MeterFilter.deny(id -> id.getName().startsWith("system")))
                .meterFilter(MeterFilter.deny(id -> id.getName().startsWith("process")))
                .meterFilter(MeterFilter.deny(id -> id.getName().startsWith("jvm")))
                .meterFilter(MeterFilter.deny(id -> id.getName().startsWith("executor")))
                .meterFilter(MeterFilter.deny(id -> id.getName().startsWith("disk")))
                .meterFilter(MeterFilter.deny(id -> id.getName().startsWith("tomcat")));
    }
}
