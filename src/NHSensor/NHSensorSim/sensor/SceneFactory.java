package NHSensor.NHSensorSim.sensor;

public class SceneFactory {

	private SceneFactory() {

	}

	public static Scene createTestTemperaureScene() {
		Scene scene = new Scene("TestScene", "The TestScene has temperaure");
		PhysicsVariable temp = new PhysicsVariable("temperature",
				new SimpleFunction());
		scene.addVariable(temp);
		return scene;
	}

	public static Scene createIntelLabTemperaureScene() {
		Scene scene = new Scene("IntelLabTemperaureScene",
				"The intelLabTemperaureScene has temperaure");
		PhysicsVariable temp = new PhysicsVariable("temperature",
				new IntellabTemperatureFunction());
		scene.addVariable(temp);
		return scene;
	}

	public static Scene createIntelLabHumidityScene() {
		Scene scene = new Scene("IntelLabHumidityScene",
				"The intelLabHumidityScene has humidity");
		PhysicsVariable humidity = new PhysicsVariable("humidity",
				new IntellabHumidityFunction());
		scene.addVariable(humidity);
		return scene;
	}

	public static Scene createIntelLabLightScene() {
		Scene scene = new Scene("IntelLabLightScene",
				"The IntelLabLightScene has temperaure");
		PhysicsVariable light = new PhysicsVariable("light",
				new IntellabLightFunction());
		scene.addVariable(light);
		return scene;
	}

	public static Scene createIntelLabVoltageScene() {
		Scene scene = new Scene("IntelLabVoltageScene",
				"The intelLabVoltageScene has temperaure");
		PhysicsVariable voltage = new PhysicsVariable("voltage",
				new IntellabVoltageFunction());
		scene.addVariable(voltage);
		return scene;
	}

	public static Scene createIntelLabScene() {
		Scene scene = new Scene("IntelLabScene",
				"The intelLabScene has temperaure,humidity,light,voltage");

		PhysicsVariable temp = new PhysicsVariable("temperature",
				new IntellabTemperatureFunction());
		scene.addVariable(temp);

		PhysicsVariable humidity = new PhysicsVariable("humidity",
				new IntellabHumidityFunction());
		scene.addVariable(humidity);

		PhysicsVariable light = new PhysicsVariable("light",
				new IntellabLightFunction());
		scene.addVariable(light);

		PhysicsVariable voltage = new PhysicsVariable("voltage",
				new IntellabVoltageFunction());
		scene.addVariable(voltage);
		return scene;
	}
}
