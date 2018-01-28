package NHSensor.NHSensorSim.ui.mainFrame;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import NHSensor.NHSensorSim.algorithm.AlgorithmModel;
import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;
import com.l2fprod.common.propertysheet.DefaultProperty;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;

public class AlgorithmParamPropertyPage extends JPanel {
	public static final Class THIS_CLASS = AlgorithmParamPropertyPage.class;
	final AlgorithmModel algorithmModel;
	Field fields[];
	Method[] setMethods;
	String[] fieldNames;
	Property[] properties;

	public AlgorithmParamPropertyPage(AlgorithmModel algorithmModel) throws Exception {
		this.algorithmModel = algorithmModel;
		Class experimentParamClass = Class.forName(algorithmModel.getAlgorithmProperty().getExperimentParamClassFullName());
		setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		fields = ReflectUtil.getAllFields(experimentParamClass);
		setMethods = new Method[fields.length];
		fieldNames = new String[fields.length];
		Property[] tempProperties = new Property[fields.length];
		String fieldName, firstLetter, setMethodName;
		Field field;
		
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			fieldName = field.getName();
			firstLetter = fieldName.substring(0, 1).toUpperCase();
			setMethodName = "set" + firstLetter + fieldName.substring(1);
			try {
				setMethods[i] = experimentParamClass.getMethod(setMethodName,
						new Class[] { field.getType() });
				fieldNames[i] = fieldName;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} 
		}

		final PropertySheetPanel sheet = new PropertySheetPanel();
		sheet.setMode(PropertySheet.VIEW_AS_FLAT_LIST);
		
		int j=0;
		for(int i=0;i<fieldNames.length;i++) {
			if(fieldNames[i]!=null) 
				tempProperties[j++] = new NodeComponentProperty(i);
		}
		
		properties = new Property[j];
		for(int i=0;i<j;i++) {
			properties[i] = tempProperties[i];
		}

		sheet.setProperties(properties);
		sheet.readFromObject(algorithmModel.getExperimentParam());
		setLayout(new BorderLayout(0, 0));
		sheet.setDescriptionVisible(false);
		sheet.setSortingCategories(true);
		sheet.setSortingProperties(true);
		sheet.setBorder(new TitledBorder("算法输入参数"));
		add(sheet);

		// everytime a property change, update the button with it
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Property prop = (Property) evt.getSource();
				prop.writeToObject(AlgorithmParamPropertyPage.this.algorithmModel.getExperimentParam());
				try {
					AlgorithmParamPropertyPage.this.algorithmModel.setExperimentParam(AlgorithmParamPropertyPage.this.algorithmModel.getExperimentParam());
					AlgorithmParamPropertyPage.this.algorithmModel.updateViews();				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		sheet.addPropertySheetChangeListener(listener);
	}
	
	public ExperimentParam getParam() {
		return algorithmModel.getExperimentParam();
	}

	public class NodeComponentProperty extends DefaultProperty {
		public NodeComponentProperty(int i) {
			String name = fieldNames[i];
			setName(name);
			setDisplayName(name);
			setShortDescription("");
			setType(fields[i].getType());
		}
	}
}
