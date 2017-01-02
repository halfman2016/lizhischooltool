package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Module.Student;
import main.util.MDBTools;
import main.view.SwingMain;

public class Main extends Application {

    private ObservableList<Student> students= FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//        primaryStage.setTitle("砺志后台管理");
//        primaryStage.setScene(new Scene(root, 640, 480));
//        primaryStage.show();
        SwingMain swingMain=new SwingMain();


    }

    public ObservableList<Student> getStudents(){
        MDBTools mdbTools=new MDBTools();
        students= (ObservableList<Student>) mdbTools.getStus();
        return students;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
