package NHSensor.NHSensorSim.ui.mainFrame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JPanel;
import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;
import com.l2fprod.common.propertysheet.DefaultProperty;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;

public class ObjectPropertyPage extends JPanel {
	public static final Class THIS_CLASS = ObjectPropertyPage.class;
	String className;
	Class objectClass;
	final Object object;
	Field fields[];
	Method[] setMethods;
	String[] fieldNames;
	Property[] properties;
	
	public ObjectPropertyPage(String className, Object object) throws Exception {
		objectClass = Class.forName(className);
		this.object = object;
		setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		fields = ReflectUtil.getAllFields(objectClass);
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
				setMethods[i] = objectClass.getMethod(setMethodName,
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
		sheet.readFromObject(object);
		sheet.setDescriptionVisible(true);
		sheet.setSortingCategories(true);
		sheet.setSortingProperties(true);
		add(sheet, "*");

		// everytime a property change, update the button with it
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Property prop = (Property) evt.getSource();
				prop.writeToObject(ObjectPropertyPage.this.object);
			}
		};
		sheet.addPropertySheetChangeListener(listener);
	}
	
	
	public Object getObject() {
		return object;
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
