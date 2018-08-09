package org.wildfly.swarm.examples.vaadin;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


/**
 * @author Sven Ruppert
 */

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class VaadinUI extends Composite<Div> {

  public VaadinUI() {
    getContent()
        .add(new Button() {
               {
                 setText("click me!");
                 addClickListener(event -> {
                   final Notification notification = new Notification();
                   notification.add(
                       new VerticalLayout(
                           new Label("Hello, I am a notification with components!"),
                           new Button() {
                             {
                               setText("Bye!");
                               addClickListener(e -> notification.close());
                             }
                           }
                       ));
                   notification.setPosition(Notification.Position.MIDDLE);
                   notification.open();
                 });
               }
             }
        );
  }
}
