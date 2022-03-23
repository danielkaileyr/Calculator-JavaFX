// Kailey Daniel
// CPS 240, HW8
// This program simulates a simple pocket calculator
// User input can be gathered from buttons or keyboard
package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.event.*;
import javafx.scene.input.KeyEvent;

public class CalculatorFX extends Application {

	private TextField display;
	String operator;
	double left, right;
	boolean leftDone, rightDone;

	@Override
	public void start(Stage stage) {

		//Create array of buttons
		Button[] buttons = new Button[16];
		for (int i = 0; i < 10; i++) {
			buttons[i] = new Button(String.valueOf(i));
		}
		buttons[10] = new Button("=");
		buttons[11] = new Button("C");
		buttons[12] = new Button("+");
		buttons[13] = new Button("-");
		buttons[14] = new Button("*");
		buttons[15] = new Button("/");

		// Create BorderPane and GridPane to create GUI layout
		BorderPane border = new BorderPane();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		display = new TextField(" ");
		// Display not editable, allows keyboard input to not "double" in textfield
		display.setEditable(false);
		// Set location of textfield to top
		border.setTop(display);

		// Set location of buttons on grid
		grid.add(buttons[0], 1, 3);
		grid.add(buttons[1], 0, 0);
		grid.add(buttons[2], 1, 0);
		grid.add(buttons[3], 2, 0);
		grid.add(buttons[4], 0, 1);
		grid.add(buttons[5], 1, 1);
		grid.add(buttons[6], 2, 1);
		grid.add(buttons[7], 0, 2);
		grid.add(buttons[8], 1, 2);
		grid.add(buttons[9], 2, 2);
		grid.add(buttons[10], 2, 3);
		grid.add(buttons[11], 0, 3);
		grid.add(buttons[12], 3, 0);
		grid.add(buttons[13], 3, 1);
		grid.add(buttons[14], 3, 2);
		grid.add(buttons[15], 3, 3);

		// Set row and column restraints
		// Set grid height and width to fill
		// Set Horizontal and Vertical constraints to grow for resizing
		RowConstraints rc = new RowConstraints();
		rc.setVgrow(Priority.ALWAYS);
		rc.setFillHeight(true);
		grid.getRowConstraints().addAll(rc, rc, rc, rc);
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHgrow(Priority.ALWAYS);
		cc.setFillWidth(true);
		grid.getColumnConstraints().addAll(cc, cc, cc, cc);

		// Set size to max value for buttons
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		}

		border.setCenter(grid);

		// Create scene
		Scene scene = new Scene(border, 300, 300);

		// Set and show scene with title
		stage.setScene(scene);
		stage.setTitle("Calculator (JavaFX)");
		stage.show();

		// Usage of lambda expression for button input
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setOnAction(e -> {
				if (e.getSource() instanceof Button) {
					// String label = ((Button)e.getSource()).getText();
					Button b = (Button) e.getSource();
					String buttonLabel = b.getText();
					doOperation(buttonLabel);
				}
			});

		}
		// Usage of lambda expression for keyboard input to textfield
		display.setOnKeyPressed(e -> {
			
			String label = e.getText();
			System.out.print(label);
			doOperation(label);

		});
	}

	// Handle input to determine helper methods to be used
	private void doOperation(String label) {
		switch (label) {
		case "C":
			clear();
			break;
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			handleNumbers(label);
			break;
		case "+":
		case "-":
		case "*":
		case "/":
			handleOperator(label);
			break;
		case "=":
			handleEqual();
			break;
		}
	}

	// Clear helper method, clears textfield and resets stage of operations
	private void clear() {
		display.setText(" ");
		left = right = 0;
		leftDone = rightDone = false;
		operator = "";
	}

	// Helper method to handle numbers for use in operation
	private void handleNumbers(String label) {
		try {
			Double.parseDouble(display.getText());
			display.setText(display.getText() + label);
		} catch (Exception e) {
			display.setText(label);
		}
	}

	// Helper method to store operator for use
	private void handleOperator(String op) {
		if (!leftDone) {
			left = Double.parseDouble(display.getText());
		} else {
			handleEqual();
			left = Double.parseDouble(display.getText());
		}
		operator = op; // remember operator for later
		display.setText(op);
		leftDone = true;
	}

	// Equal helper method to perform operations on stored variables
	private void handleEqual() {
		if (leftDone) {
			right = Double.parseDouble(display.getText());
			switch (operator) {
			case "+":
				display.setText(Double.toString(left + right));
				break;
			case "-":
				display.setText(Double.toString(left - right));
				break;
			case "*":
				display.setText(Double.toString(left * right));
				break;
			case "/":
				if (right == 0)
					display.setText("Err: divide by 0");
				else
					display.setText(Double.toString(left / right));

			}
			leftDone = false;
		}
	}

	// Main method launches the application
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
