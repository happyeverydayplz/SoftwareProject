package View;

import Model.Client;
import Model.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ClassSceneController {
    public Tab introTag;
    public TabPane tabPane;
    public Button goBack;
    public Label freefor;
    public Label forsingle;
    public Button watchVideo;
    public Button delete;
    public Button subscibe;

    public Scene previousScene;
    public Label accountType;
    public Label price;
    public Label nameLabel;
    public Model.Client client;
    public Model.Class course;

    public void setCourse(Model.Class course){
        this.course = course;
    }
    public void setClient(Client client) {this.client = client;}
    public void buildScene() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SceneForClassesPlan.fxml"));
        Parent parent = loader.load();
        Scene sceneForPlan = new Scene(parent);
        SceneForClassesPlan controller = loader.getController();
        introTag.setContent(controller.pane);
        controller.textForPlanInfo.setText(course.getInfo());
        int i=1;
        for(Model.Plan plan : course.getDay_Plans()){

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SceneForClassesPlan.fxml"));
            parent = loader.load();
            sceneForPlan = new Scene(parent);
            controller = loader.getController();

            Tab tab = new Tab("Day"+i++);
            AnchorPane pane = controller.pane;
            tab.setContent(pane);//Node
            controller.textForPlanInfo.setText(plan.getPlan());
            tabPane.getTabs().add(tab);

        }
        accountType.setText((course.getRank()==0)?"Standard":"Premier");
        price.setText(((Integer)course.getPrice()).toString());
        //accountType
        //accountPrice

    }

    public void goBack(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ClientMainSceneController controller = (ClientMainSceneController) previousScene.getUserData();//get controller of previous scene
        controller.updateClassesInMyClass();
        controller.updateClassesInMainPage();
        window.setScene(previousScene);
    }

    public void watchVideo(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WatchVideo.fxml"));
        Parent WatchVideoParent = loader.load();
        Scene WatchVideoScene = new Scene(WatchVideoParent);
        stage.setScene(WatchVideoScene);
        WatchVideo controller = loader.getController();
        int index = tabPane.getSelectionModel().getSelectedIndex();
        controller.dayLabel.setText("Day: "+index);
        controller.url = course.getDay_Plans().get(index).getVideo_path();
        controller.urlLabel.setText(controller.url);
        stage.show();
    }

    public void Payment(ActionEvent actionEvent) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
        changeToPayment(course.getInfo(),course.getPrice()+"$");
        Model.Control control = new Control();
        control.AddClass(client.getId(),course.getId());
        //System.out.println("inPayment: "+course.getId());
    }

    public void changeToPayment(String item,String price) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Payment.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);
        stage.setScene(PaymentScene);
        Payment controller = loader.getController();
        controller.buildScene(item,price);
        stage.show();
    }


}
