
package org.wildfly.swarm.examples.vaadin;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 *
 * @author Matti Tahvonen
 */
@CDIUI("")
public class VaadinUI extends UI {

    @Inject
    Greeter greeter;

    @Override
    protected void init(VaadinRequest request) {
        setContent(new Button("Greet", e->Notification.show(greeter.greet())));
    }

}
