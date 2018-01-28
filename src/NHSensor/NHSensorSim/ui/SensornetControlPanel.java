package NHSensor.NHSensorSim.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SensornetControlPanel extends JPanel implements SensornetView {
	private final String Stop = "停止";
	private final String Play = "运行";
	private final String Previous = "前一个";
	private final String Next = "下一个";
	private final String EventsSlider = "事件";
	private final String SpeedSlider = "运行速度";
	private final String CanvasSizeSlider = "网络大小缩放";

	private SensornetModel sensornetModel;
	private EventController eventController;
	private JButton stop = new JButton(Stop);
	private JButton moveOn = new JButton(Play);
	private JButton previous = new JButton(Previous);
	private JButton next = new JButton(Next);
	private JSlider eventsSlider = new JSlider();
	private JSlider speedSlider = new JSlider();
	private JSlider sensorCanvasScaleSlider = new JSlider();
	private boolean isRunning = false;
	private int minSensorCanvasScale = 0;
	private int maxSensorCanvasScale = 200;
	private double scaleScale = 3;
	

	public SensornetControlPanel(SensornetModel sensornetModel,
			EventController eventController) {
		this.sensornetModel = sensornetModel;
		this.eventController = eventController;
		this.initControlPanel();
	}

	private void initButtonStatus() {
		this.showButtons();
	}

	private void initButtons() {
		this.add(stop);
		this.add(moveOn);
		this.add(previous);
		this.add(next);
		ControlButtonActionListener cbal = new ControlButtonActionListener();
		stop.addActionListener(cbal);
		moveOn.addActionListener(cbal);
		previous.addActionListener(cbal);
		next.addActionListener(cbal);
	}

	private void initEventSlider() {
		eventsSlider.setMinimum(0);
		eventsSlider.setMaximum(this.getSensornetModel().getEventsDatabase()
				.size());
		eventsSlider.setValue(eventsSlider.getMinimum());
		eventsSlider.setBorder(BorderFactory
				.createTitledBorder(EventsSlider));
		eventsSlider.setMajorTickSpacing(100);
		eventsSlider.setMinorTickSpacing(50);
		eventsSlider.setPaintTicks(true);
		eventsSlider.setPaintLabels(true);
		EventsSliderChangeListerner eventsSliderChangeListerner = new EventsSliderChangeListerner();
		eventsSlider.addChangeListener(eventsSliderChangeListerner);
		this.add(eventsSlider);
	}

	private void initSpeedSlider() {
		speedSlider.setMinimum(0);
		speedSlider.setMaximum(100);
		speedSlider.setValue((int) (this.eventController.getSpeed() * 100));
		speedSlider.setBorder(BorderFactory.createTitledBorder(SpeedSlider));
		speedSlider.setMajorTickSpacing(20);
		speedSlider.setMinorTickSpacing(2);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		SpeedSliderChangeListerner speedSliderChangeListerner = new SpeedSliderChangeListerner();
		speedSlider.addChangeListener(speedSliderChangeListerner);
		this.add(speedSlider);
	}

	private void initSensorCanvasScaleSlider() {
		sensorCanvasScaleSlider.setMinimum(minSensorCanvasScale);
		sensorCanvasScaleSlider.setMaximum(maxSensorCanvasScale);
		sensorCanvasScaleSlider.setValue(1);
		sensorCanvasScaleSlider.setBorder(BorderFactory
				.createTitledBorder(CanvasSizeSlider));
		sensorCanvasScaleSlider.setMajorTickSpacing(100);
		sensorCanvasScaleSlider.setMinorTickSpacing(10);
		sensorCanvasScaleSlider.setPaintTicks(true);
		sensorCanvasScaleSlider.setPaintLabels(true);
		SensorCanvasSizeSliderChangeListerner sensorCanvasSizeSliderChangeListerner = new SensorCanvasSizeSliderChangeListerner();
		sensorCanvasScaleSlider
				.addChangeListener(sensorCanvasSizeSliderChangeListerner);
		this.add(sensorCanvasScaleSlider);
	}

	protected void initControlPanel() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		this.initButtonStatus();
		this.initButtons();
		this.initEventSlider();
		this.initSpeedSlider();
		this.initSensorCanvasScaleSlider();
	}

	public class EventsSliderChangeListerner implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				try {
					sensornetModel.setCurrentEventIndex(source.getValue() - 1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public class SpeedSliderChangeListerner implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				try {
					eventController
							.setSpeed((double) source.getValue() / 1000.0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public class SensorCanvasSizeSliderChangeListerner implements
			ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				try {
					sensornetModel.setSensorCanvasSizeScale(source.getValue()
							/ scaleScale);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void showButtons() {
		if (getSensornetModel().isEnd())
			this.isRunning = false;

		if (this.isRunning) {
			stop.setEnabled(!getSensornetModel().isEnd());
			moveOn.setEnabled(!getSensornetModel().isEnd());
		} else {
			stop.setEnabled(false);
			moveOn.setEnabled(!getSensornetModel().isEnd());
		}
		previous.setEnabled(!getSensornetModel().isBegin());
		next.setEnabled(!getSensornetModel().isEnd());
	}

	class ControlButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JButton button = (JButton) arg0.getSource();

			if (button.getText().equals(Stop)) {
				eventController.setStarted(false);
				isRunning = false;
				showButtons();
			} else if (button.getText().equals(Play)) {
				eventController.setStarted(true);
				isRunning = true;
				showButtons();
			} else if (button.getText().equals(Previous)) {
				try {
					if (!getSensornetModel().isBegin())
						getSensornetModel().movePrevious(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				showButtons();

			} else if (button.getText().equals(Next)) {
				try {
					if (!getSensornetModel().isEnd())
						getSensornetModel().moveNext(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				showButtons();
			} else {
			}
		}
	}

	public SensornetModel getSensornetModel() {
		return this.sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public void updateSensornetView() {
		this.eventsSlider.setValue(this.sensornetModel.getEventsDatabase()
				.getCurrentEventIndex() + 1);

		showButtons();
	}

}
