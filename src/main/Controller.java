package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Module.Student;
import main.Module.Teacher;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML
    private Label label;
    @FXML
    private Button button;

    @FXML
    private TableView<Student> stutable;

    @FXML
    private TableColumn<Student,String> stuName;
    private TableColumn<Student,String> stuGradeclassid;
    private Main mainapp;

    private Student student;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // stuName.setCellValueFactory(cellData->cellData.getValue().getName());

    }

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        label.setText("hello World");
    }

    public void setMainapp(Main mainapp)
    {
        this.mainapp=mainapp;
        stutable.setItems(mainapp.getStudents());
    }
}
