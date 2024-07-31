package com.alabtaal.school;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.enums.FxmlView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFXApplication extends Application {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    @Override
    public void init(){
        springContext=springBootApplicationContext();

    }

    public void start(Stage stage)  {
        stageManager=springContext.getBean(StageManager.class,stage);
        displayInitialScene();

    }

    protected void displayInitialScene()
    {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @Override
    public void stop()
    {
        springContext.close();
    }


    private ConfigurableApplicationContext springBootApplicationContext()
    {
        final SpringApplicationBuilder builder=new SpringApplicationBuilder(SchoolApplication.class);
        builder.headless(false);
        final String[] args=getParameters().getRaw().toArray(new String[0]);
        return builder.run(args);
    }
}
