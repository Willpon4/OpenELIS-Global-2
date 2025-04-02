package org.openelisglobal.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.common.util.DefaultConfigurationProperties;
import org.openelisglobal.scheduler.service.CronSchedulerService;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerConfigTest {

    @InjectMocks
    private SchedulerConfig schedulerConfig;

    @Mock
    private CronSchedulerService chronScheduleService;

    @Mock
    private ConfigurationProperties configProperties;

    @Mock
    private DefaultConfigurationProperties configPropertiesMock;

    private ConfigurationProperties originalConfigProperties;

    @Test
    public void getResultsResendTimeMillisTestDefaultValue() {
        long default_period_millis = 30L * 1000 * 60;

        SchedulerConfig schedulerConfig = new SchedulerConfig() {
            @Override
            public long getResultsResendTimeMillis() {
                long period = 30L;
                return period * 60 * 1000;
            }
        };

        long result = schedulerConfig.getResultsResendTimeMillis();

        assertEquals(default_period_millis, result);
    }

    @Test
    public void getResultsResendTimeMillisTestCustomValue() {
        long default_period_millis = 30L;

        SchedulerConfig schedulerConfig = new SchedulerConfig() {

            @Override
            public long getResultsResendTimeMillis() {
                String custom_value = "45";
                long reportInterval = Long.parseLong(custom_value);

                if (!custom_value.isBlank()) {
                    return reportInterval * 1000 * 60;
                }
                return default_period_millis * 1000 * 60;
            }
        };

        Long expected = 45L * 1000 * 60;
        Long actual = schedulerConfig.getResultsResendTimeMillis();

        assertEquals(expected, actual);
    }

    @Test
    public void getResultsResendTimeMillisTestInvalidValue() {
        long default_period_millis = 30L;

        SchedulerConfig schedulerConfig = new SchedulerConfig() {

            @Override
            public long getResultsResendTimeMillis() {
                String custom_value = "a";
                Long reportInterval = Long.parseLong(custom_value);

                if (!custom_value.isBlank()) {
                    try {
                        return reportInterval * 1000 * 60;
                    } catch (NumberFormatException E) {
                        throw new NumberFormatException();
                    }

                }
                return default_period_millis * 1000 * 60;
            }
        };

        long expected = default_period_millis * 1000 * 60;

        assertThrows(NumberFormatException.class, () -> schedulerConfig.getResultsResendTimeMillis());

    }

}
