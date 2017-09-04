package org.wildfly.swarm.examples.netflix.ribbon.events;

import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.netflix.ribbon.proxy.annotation.ResourceGroup;
import com.netflix.ribbon.proxy.annotation.TemplateName;
import io.netty.buffer.ByteBuf;

/**
 * @author Bob McWhirter
 */
@ResourceGroup( name="example-ribbon-consul-time" )
public interface TimeService {

    TimeService INSTANCE = Ribbon.from(TimeService.class);

    @TemplateName("currentTime")
    @Http(
            method = Http.HttpMethod.GET,
            uri = "/"
    )
    @Hystrix(
            fallbackHandler = TimeFallbackHandler.class
    )
    RibbonRequest<ByteBuf> currentTime();

}
