package com.PiratesOfTheSiliconValley.LibSys.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "/about", layout = Navbar.class)
@PageTitle("Om oss")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class AboutUsView extends VerticalLayout {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    public AboutUsView() {

        Accordion accordion = new Accordion();

        accordion.setWidth("1200px");

        VerticalLayout tab1 = new VerticalLayout();
        accordion.add("Med anledning av covid-19", tab1);
        TextArea textArea = new TextArea();
        textArea.setReadOnly(true);
        textArea.setWidth("700px");
        textArea.setValue("""
                Du bör inte vistas i inomhusmiljöer där människor samlas just nu. 
                Besök därför bara biblioteket om du har ett viktigt ärende. 
                Hjälp till att stoppa smittspridningen.
                                
                    - Det finns ett begränsat antal sittplatser i biblioteket. 
                    Stolarna i lokalen får inte flyttas.
                    
                    - Studenter som söker studieplatser hänvisas till respektive skola/högskola.
                    
                    -Stolar vid datorplatserna är endast till för användning av bibliotekets 
                    stationära datorer. Det är inte tillåtet att dra ut datorernas sladdar.
                    
                    -Tiden vid datorerna är begränsad till 30 minuter per person och dag.
                    
                    - Ingen individuell hjälp vid datorer, skanner, kopiering.
                    
                    - Vi kontrollerar antalet besökare i lokalen med hjälp av vakt.
                    
                    - Håll avståndet till andra besökare och till personalen.
                    
                    - Håll ditt ärende kort.            
                                
                Tack för er förståelse och hänsyn!""");
        tab1.add(textArea);

        VerticalLayout tab2 = new VerticalLayout();
        accordion.add("Om biblioteket", tab2);
        TextArea textArea2 = new TextArea();
        textArea2.setReadOnly(true);
        textArea2.setWidth("700px");
        textArea2.setValue("""
                På Jisho biblioteket finns skönlitteratur och facklitteratur inom olika ämnesområden, 
                samt Ljudbiblioteket med tal- och ljudböcker. 
                Vi har cirka 11 böcker och besöks dagligen av ungefär 1 besökare. 
                Dessutom ordnar vi ett stort antal seminarium, så kom och hälsa på!""");
        tab2.add(textArea2);

        VerticalLayout tab3 = new VerticalLayout();
        accordion.add("Vi som jobbar här", tab3);
        TextArea textArea3 = new TextArea();
        textArea3.setReadOnly(true);
        textArea3.setWidth("700px");
        textArea3.setValue("""
                Bibliotekarie Britt-Marie
                BiblioteksChef Chad""");
        tab3.add(textArea3);

        VerticalLayout tab4 = new VerticalLayout();
        accordion.add("Besök oss", tab4);
        TextArea textArea4 = new TextArea();
        textArea4.setReadOnly(true);
        textArea4.setWidth("700px");
        textArea4.setValue("""
                - Adress
                - Karta?!""");
        tab4.add(textArea4);

        VerticalLayout tab5 = new VerticalLayout();
        accordion.add("Kontakt", tab5);
        TextArea textArea5 = new TextArea();
        textArea5.setReadOnly(true);
        textArea5.setWidth("700px");
        textArea5.setValue("""
                Telefonnummer
                    Bibliotekens kundtjänst (mån-fre, kl 9-13)
                    08-340 30 900
                    Jisho biblioteket
                    08-340 31 020
                    Beställning av talböcker (mån–fre, kl. 10–15)
                    08-340 31 160
                
                E-post
                    	kundtjanst.jisho@bibliotek.se""");
        tab5.add(textArea5);

        add(accordion);
    }
}
