import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	public void start(Stage primaryStage) {
		AnchorPane root = new AnchorPane();
		Button startBtn = new Button("Start");
		ChoiceBox<String> sortAlgo = new ChoiceBox<String>();
		sortAlgo.getItems().addAll("Bubble Sort", "Selection Sort","Bucket Sort","Merge Sort");
		//store information of buckets for sorting
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<10;i++) {
			ArrayList<Integer> bucket = new ArrayList<Integer>();
			buckets.add(bucket);
		}
		
		
		GridPane bubblePane  = new GridPane();
		bubblePane.setPadding(new Insets(20));
		bubblePane.setHgap(5);
		bubblePane.setVgap(5);
		
		GridPane bucketPane = new GridPane();
		bucketPane.setPadding(new Insets(20.0));
		bucketPane.setVgap(40);
		bucketPane.setHgap(5);
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		ArrayList<Button> buttons = new ArrayList<Button>();
		Button[][] swaps = new Button[10][10];
		
		int[] arr = {10,47,3,21,53,9,10,32,50,15};
		int[] arr1 = {10,47,3,21,53,9,10,32,50,15};
		int max = 53+1;
		ArrayList<TextField> tFields = new ArrayList<TextField>();
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		//Generate default data
		for(int i=0;i<10;i++) {
			TextField tf = new TextField();
			tf.setText(Integer.toString(arr[i]));
			tf.setEditable(false);
			tf.setAlignment(Pos.CENTER);
			tFields.add(tf);
			Rectangle rect = new Rectangle(100,arr[i]*14);
			rect.setFill(Color.BROWN);
			rectangles.add(rect);
			bubblePane.add(rect, i+1, 0);
			GridPane.setValignment(rect, VPos.BOTTOM);
			GridPane.setHalignment(rect, HPos.CENTER);
			GridPane.setHgrow(rect,Priority.ALWAYS);
			bubblePane.add(tf,i+1,1);
		}
		//bubble sort
		
		Timeline bubbleTimeline = new Timeline();
		bubbleTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
			int k=0;
			int i = arr.length-2;
			@Override
			public void handle(ActionEvent event) {
				//set default color
				for(int m=0;m<=arr.length-1;m++) {
					rectangles.get(m).setFill(Color.BROWN);
				}
				//condition to exit loop
				if(k==arr.length-2) {
					bubbleTimeline.stop();
					sortAlgo.setDisable(false);
					k=0;
					i=arr.length-2;
					return;
				}
				//compare two adjacent array elements
				if(arr[i]>arr[i+1]) {
					int temp = arr[i+1];
					arr[i+1] = arr[i];
					arr[i] = temp;
				}
				//set color for chosen elements
				rectangles.get(i).setFill(Color.YELLOWGREEN);
				rectangles.get(i+1).setFill(Color.YELLOWGREEN);
				//set value for rectangles and text fields
				for(int j=0;j<10;j++) {
					tFields.get(j).setText(Integer.toString(arr[j]));
					rectangles.get(j).setHeight(arr[j]*14);
				}
				i--;
				if (i<k){
					k++;
					i=arr.length-2;
				}
			}
		}));
		bubbleTimeline.setCycleCount(Animation.INDEFINITE);
		bubbleTimeline.setAutoReverse(false);
		
		//Selection Sort

		Timeline selectionTimeline = new Timeline();
		selectionTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
			int i = 0;
			int last = arr.length - 1;
			int imax = 0;
			int max = -999999;
			@Override
			public void handle(ActionEvent event) {
				//set default color
				for(int m=0;m<=arr.length-1;m++) {
					rectangles.get(m).setFill(Color.BROWN);
				}
				//Return when done sorting
				if(last < 0) {
					selectionTimeline.stop();
					sortAlgo.setDisable(false);
					i = 0;
					last = arr.length - 1;
					imax = 0;
					max = -999999;
					return;
				}
				
				//Compare to set max
				if(arr[i] > max) {
					imax = i;
					max = arr[i];
				}
				
				//Last element, swap
				if(i == last) {
					int temp = arr[i];
					arr[i] = arr[imax];
					arr[imax] = temp;
					i = -1;
					imax = last;
					max = -999999;
					last -= 1;
				}
				//set color for chosen elements
				rectangles.get(imax).setFill(Color.BLUEVIOLET);
				if(i != -1) rectangles.get(i).setFill(Color.YELLOWGREEN);
				//set value for rectangles and text fields
				for(int j=0;j<10;j++) {
					tFields.get(j).setText(Integer.toString(arr[j]));
					rectangles.get(j).setHeight(arr[j]*14);
				}
				
				i += 1;
			}
		}));
		selectionTimeline.setCycleCount(Animation.INDEFINITE);
		selectionTimeline.setAutoReverse(false);
		
		// MergeSort
		Timeline mergeTimeline = new Timeline();
		mergeTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
			int[] L = new int [100];
		    int[] R = new int [100]; 
		    int cur_size = 1;
		    int left_start = 0;
		    int right_end;
			
			@Override
			public void handle(ActionEvent event) {
				//set default color
				for(int m=0;m<=arr.length-1;m++) {
					rectangles.get(m).setFill(Color.BROWN);
				}
				//SET SIZE AND TARGET PHASE
				if (cur_size > arr.length - 1) {
					mergeTimeline.stop();
					sortAlgo.setDisable(false);
					cur_size = 1;
					left_start = 0;
					right_end = 0;
					return;
				}
				if (left_start >= arr.length - 1) 
				{
					System.out.print("double\n");
					left_start = 0;
					cur_size *= 2;
				}
				
				if(left_start + 2*cur_size - 1 < arr.length - 1)
				{
					right_end = left_start + 2*cur_size - 1;
				}
				else {
					right_end = arr.length - 1;
				}
				
				//MERGE PHASE
				
				int l = left_start;
				int m = left_start + cur_size - 1;
				int r = right_end;
				for(int index=l;index<=r;index++) {
					rectangles.get(index).setFill(Color.YELLOWGREEN);
				}
				
				int i, j, k; 
				k = 0;
			
			    int n1 = m - l + 1; 
			    int n2 =  r - m; 
			    
			    System.out.println("n1 = " + n1);
			    for (i = 0; i < n1; i++) 
			    {
			    	//System.out.print(l + i + " ");
			    	if(l + i >= arr.length)
			    		break;
			        L[i] = arr[l + i]; 
			        
			    }
			    
			    for (j = 0; j < n2; j++) 
			    {
			    	System.out.print(m + 1 + j + " ");
			        R[j] = arr[m + 1 + j]; 
			        
			    }
			    
			    i = 0; 
			    j = 0; 
			    k = l; 
			    while (i < n1 && j < n2) 
			    { 
			        if (L[i] <= R[j]) 
			        { 
			            arr[k] = L[i]; 
			            i++; 
			        } 
			        else
			        { 
			            arr[k] = R[j]; 
			            j++; 
			        } 
			        k++; 
			    }
			    
			    while (i < n1) 
			    { 
			    	if (k >= arr.length) {
						break;
					}
			        arr[k] = L[i]; 
			        i++; 
			        k++; 
			    } 
			  
			    while (j < n2) 
			    { 
			    	if (k >= arr.length) {
						break;
					}
			        arr[k] = R[j]; 
			        j++; 
			        k++; 
			    } 
				
				//set value for rectangles and text fields
				for(int index=0;index < 10;index++) {
					tFields.get(index).setText(Integer.toString(arr[index]));
					rectangles.get(index).setHeight(arr[index]*14);
				}
				System.out.print("\n");
				
				left_start += 2*cur_size;
				
			}
		}));
		mergeTimeline.setCycleCount(Animation.INDEFINITE);
		mergeTimeline.setAutoReverse(false);
		
		//bucket sort
		for(int i=0;i<10;i++) {
			Button button = new Button(Integer.toString(arr[i]));
			GridPane.setHalignment(button, HPos.CENTER);
			buttons.add(button);
			bucketPane.add(button,i,1);
			Rectangle rectangle = new Rectangle(100, 20);
			rectangle.setFill(Color.BROWN);
			rects.add(rectangle);
			GridPane.setValignment(rectangle, VPos.BOTTOM);
			GridPane.setHalignment(rectangle, HPos.CENTER);
			bucketPane.add(rectangle, i, 10);	
		}
		//add button quanh map
		for(int i=2;i<10;i++)
			for(int j=0;j<10;j++) {
				Button button = new Button();
				GridPane.setHalignment(button, HPos.CENTER);
				button.setVisible(false);
				GridPane.setHalignment(button, HPos.CENTER);
				swaps[i][j]=button;
				bucketPane.add(button, j, i);
			}
		
		Timeline bucketTimeline = new Timeline();
		bucketTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
			int i=0; //index of array to push to bucket
			int j=0; //index of bucket
			int k=0; //index of bucket to push back to array
			int m=0; //index of elements in bucket
			int n=0; //index of array pushed back from buckets
			@Override
			public void handle(ActionEvent event) {
				if(i<arr.length) {
					String value = buttons.get(i).getText();
					buttons.get(i).setVisible(false);
					int index = arr[i]*10/max;
					Button btn = swaps[9-buckets.get(index).size()][index];
					rects.get(index).setFill(Color.YELLOW);
					btn.setText(value);
					btn.setVisible(true);
					buckets.get(index).add(Integer.parseInt(value));
				}else {
					for(int x=0;x<arr.length;x++) rects.get(x).setFill(Color.BROWN);
					if(j<arr.length) {
						Collections.sort(buckets.get(j));
						rects.get(j).setFill(Color.YELLOW);
						for(int l=0;l<buckets.get(j).size();l++) {
							swaps[9-l][j].setText(Integer.toString(buckets.get(j).get(l)));
						}
					}else {
						if(k<arr.length) {
							for(int x=0;x<arr.length;x++) rects.get(x).setFill(Color.BROWN);
							rects.get(k).setFill(Color.YELLOW);
							if(m<buckets.get(k).size()) {
								String value = swaps[9-m][k].getText();
								swaps[9-m][k].setVisible(false);
								buttons.get(n).setText(value);
								buttons.get(n).setVisible(true);
								n++;
								m++;
							}else {
								m=0;
								k++;
							}
						}else {
							bucketTimeline.stop();
							sortAlgo.setDisable(false);
							i=0;
							k=0;
							j=0;
							m=0;
							n=0;
							return;
						}
					}
					j++;
				}
				i++;
			}
		}));
		bucketTimeline.setCycleCount(Animation.INDEFINITE);
		bucketTimeline.setAutoReverse(false);
		
		AnchorPane.setTopAnchor(startBtn, 600.0);
		AnchorPane.setLeftAnchor(startBtn, 20.0);
		AnchorPane.setRightAnchor(startBtn, 1150.0);
		
		AnchorPane.setTopAnchor(sortAlgo, 400.0);
		AnchorPane.setLeftAnchor(sortAlgo, 20.0);
		AnchorPane.setRightAnchor(sortAlgo, 1150.0);
		
		AnchorPane.setTopAnchor(bubblePane, 5.0);
		AnchorPane.setLeftAnchor(bubblePane, 100.0);
		AnchorPane.setRightAnchor(bubblePane, 5.0);
		
		AnchorPane.setTopAnchor(bucketPane, 5.0);
		AnchorPane.setLeftAnchor(bucketPane, 100.0);
		AnchorPane.setRightAnchor(bucketPane, 5.0);
//		
//		AnchorPane.setTopAnchor(mergePane, 5.0);
//		AnchorPane.setLeftAnchor(mergePane, 100.0);
//		AnchorPane.setRightAnchor(mergePane, 5.0);
		
		root.getChildren().addAll(startBtn,sortAlgo);

		ChangeListener<String> changeAlgo = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				for(int i=0;i<10;i++) {
					arr[i]=arr1[i];
				}
				if(newValue.equals("Bubble Sort")) {
					root.getChildren().remove(bubblePane);
					root.getChildren().remove(bucketPane);
					bubblePane.getChildren().clear();
					tFields.clear();
					rectangles.clear();
					for(int i=0;i<10;i++) {
						TextField tf = new TextField();
						tf.setText(Integer.toString(arr[i]));
						tf.setEditable(false);
						tf.setAlignment(Pos.CENTER);
						tFields.add(tf);
						Rectangle rect = new Rectangle(100,arr[i]*14);
						rect.setFill(Color.BROWN);
						rectangles.add(rect);
						bubblePane.add(rect, i+1, 0);
						GridPane.setValignment(rect, VPos.BOTTOM);
						GridPane.setHalignment(rect, HPos.CENTER);
						GridPane.setHgrow(rect,Priority.ALWAYS);
						bubblePane.add(tf,i+1,1);
					}
					root.getChildren().add(bubblePane);
				}
				else if(newValue == "Selection Sort") {
					root.getChildren().remove(bubblePane);
					root.getChildren().remove(bucketPane);
					tFields.clear();
					rectangles.clear();
					bubblePane.getChildren().clear();
					for(int i=0;i<10;i++) {
						TextField tf = new TextField();
						tf.setText(Integer.toString(arr[i]));
						tf.setEditable(false);
						tf.setAlignment(Pos.CENTER);
						tFields.add(tf);
						Rectangle rect = new Rectangle(100,arr[i]*14);
						rect.setFill(Color.BROWN);
						rectangles.add(rect);
						bubblePane.add(rect, i+1, 0);
						GridPane.setValignment(rect, VPos.BOTTOM);
						GridPane.setHalignment(rect, HPos.CENTER);
						GridPane.setHgrow(rect,Priority.ALWAYS);
						bubblePane.add(tf,i+1,1);
					}
					root.getChildren().add(bubblePane);
				}
				else if(newValue.equals("Merge Sort")) {
					root.getChildren().remove(bubblePane);
					root.getChildren().remove(bucketPane);
					tFields.clear();
					rectangles.clear();
					bubblePane.getChildren().clear();
					for(int i=0;i<10;i++) {
						TextField tf = new TextField();
						tf.setText(Integer.toString(arr[i]));
						tf.setEditable(false);
						tf.setAlignment(Pos.CENTER);
						tFields.add(tf);
						Rectangle rect = new Rectangle(100,arr[i]*14);
						rect.setFill(Color.BROWN);
						rectangles.add(rect);
						bubblePane.add(rect, i+1, 0);
						GridPane.setValignment(rect, VPos.BOTTOM);
						GridPane.setHalignment(rect, HPos.CENTER);
						GridPane.setHgrow(rect,Priority.ALWAYS);
						bubblePane.add(tf,i+1,1);
					}
					root.getChildren().add(bubblePane);	
				}
				else if(newValue.equals("Bucket Sort")) {
					for(int i=0;i<10;i++) {
						arr[i]=arr1[i];
					}
					root.getChildren().remove(bubblePane);
					root.getChildren().remove(bucketPane);
					bucketPane.getChildren().clear();
					buckets.clear();
					buttons.clear();
					rects.clear();
					for(int i=0;i<10;i++) {
						ArrayList<Integer> bucket = new ArrayList<Integer>();
						buckets.add(bucket);
					}
					for(int i=0;i<10;i++) {
						Button button = new Button(Integer.toString(arr[i]));
						GridPane.setHalignment(button, HPos.CENTER);
						buttons.add(button);
						bucketPane.add(button,i,1);
						Rectangle rectangle = new Rectangle(100, 20);
						rectangle.setFill(Color.BROWN);
						rects.add(rectangle);
						GridPane.setValignment(rectangle, VPos.BOTTOM);
						GridPane.setHalignment(rectangle, HPos.CENTER);
						bucketPane.add(rectangle, i, 10);	
					}
					//add button quanh map
					for(int i=2;i<10;i++)
						for(int j=0;j<10;j++) {
							Button button = new Button();
							GridPane.setHalignment(button, HPos.CENTER);
							button.setVisible(false);
							GridPane.setHalignment(button, HPos.CENTER);
							swaps[i][j]=button;
							bucketPane.add(button, j, i);
						}
					
					root.getChildren().add(bucketPane);		
				}
			}
		};
		sortAlgo.getSelectionModel().selectedItemProperty().addListener(changeAlgo);
		
		Scene scene = new Scene(root,1260,800);
		startBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sortAlgo.setDisable(true);
				if(sortAlgo.getValue() == "Bubble Sort")
					bubbleTimeline.play();
				else if(sortAlgo.getValue() == "Selection Sort")
				{
					System.out.print("selection sort");
					selectionTimeline.play();
				}
				else if(sortAlgo.getValue() == "Merge Sort")
				{
					System.out.print("Merge sort");
					mergeTimeline.play();
				}else {
					System.out.println("Bucket sort");
					bucketTimeline.play();
				}
			}
		});
		sortAlgo.getSelectionModel().select(0);
		primaryStage.setTitle("Sort Visualization");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
