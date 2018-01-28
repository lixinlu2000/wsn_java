/** 
 * <p>This package was created using MATLAB Builder JA. The classes included in this 
 * package are wrappers around M functions which were used when compiling this package 
 * in MATLAB. These classes have public methods that provide access to the M functions 
 * used by MATLAB Builder JA during compilation.</p> 
 * Following are the additional requirements for successfully using this package: 
 * <h3>MATLAB Compiler Runtime (MCR)</h3>
 * <p>The classes which are part of this package have dependencies on native libraries 
 * that together form the MCR. The MCR can be made available by either installing 
 * MATLAB or running MCRInstaller. This package will work only with MCR version 7.10. 
 * Please contact the creator of this package for specific details about the MCR 
 * (e.g what version of MATLAB it originated with since MCR version is tied to the 
 * version of MATLAB).</p>
 * <p>Creating the first instance of one of the classes from this package is more time
 * consuming than creating subsequent instances since the native libraries from the MCR
 * get loaded the first time; the subsequent instances of classes are created more 
 * quickly since they use the already loaded native libraries.</p>
 * <h3>javabuilder.jar</h3> 
 * <p>This file provides classes that act as the bridge between your application and 
 * the MCR. The jar file is part of MCR and can be found in the 
 * $MCR/toolbox/javabuilder/jar directory ($MCR is the root of either MATLAB or the 
 * MCRInstaller installation). This package will only work with the javabuilder.jar file
 * included with MCR version 7.10. </p> 
 * <p><code>com.mathworks.toolbox.javabuilder.MWArray</code> is one of many data conversion
 * classes provided in javabuilder.jar. MWArray is an abstract class representing a 
 * MATLAB array. Each MATLAB array type has a corresponding concrete class type in the 
 * MWArray class hierarchy. The public methods that represent M functions, for the classes 
 * of this package, can take instances of these concrete classes as input. These methods 
 * can also take native JAVA primitive or array types as input. These native types get 
 * converted to appropriate MWArray types using data conversion rules provides by 
 * MATLAB Builder JA e.g a JAVA primitive type double gets converted into an instance of
 * MWNumericArray (a subclass of MWArray)</p> 
 */
package resta;
