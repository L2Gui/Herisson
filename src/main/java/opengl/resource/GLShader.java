package opengl.resource;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL20;
 

import org.lwjgl.opengl.GLContext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
 






import org.lwjgl.BufferUtils;
 
public class GLShader implements IGLResource {
	private String vertexPath;
	private String fragmentPath;
	private boolean isInitialized;
	private int programf = 0,programv = 0,programs;
	
	public GLShader(String vertexPath, String fragmentPath) {
		this.vertexPath = vertexPath;
		this.fragmentPath = fragmentPath;
		this.isInitialized = false;
	}
	public void testCapabilities(){
        ContextCapabilities cp = GLContext.getCapabilities();
        System.out.println("Shaders: "+cp.GL_AMD_vertex_shader_layer+ " "+cp.GL_AMD_multi_draw_indirect+" "+cp.GL_AMD_sample_positions+" "+cp.GL_AMD_vertex_shader_tessellator+" "+cp.GL_AMD_vertex_shader_viewport_index);
        System.out.println("opengl 44: "+cp.OpenGL44);
        System.out.println("opengl 40: "+cp.OpenGL40);
        System.out.println("opengl 32: "+cp.OpenGL32);
        System.out.println("opengl 20: "+cp.OpenGL20);
        System.out.println("opengl 15: "+cp.OpenGL15);
        System.out.println("opengl 12: "+cp.OpenGL12);
        System.out.println("opengl 11: "+cp.OpenGL11);
    }
	@Override
	public void init() {
		if (this.isInitialized() || this.vertexPath == null || this.fragmentPath == null) {
			System.err.println("Paths are invalid or this shader is already initialized");
			return;
		}

        testCapabilities();

        if (GLContext.getCapabilities().OpenGL32) {
			this.vertexPath = "resource/shader/" + this.vertexPath;
			this.fragmentPath = "resource/shader/" + this.fragmentPath;
		} else {
            this.vertexPath = "resource/shader/1.2/" + this.vertexPath;
			this.fragmentPath = "resource/shader/1.2/" + this.fragmentPath;
		}
		
		this.programv = createShader(this.vertexPath, GL_VERTEX_SHADER);
        this.programf = createShader(this.fragmentPath, GL_FRAGMENT_SHADER);
 
        this.programs = glCreateProgram();
        glAttachShader(this.programs, this.programf);
        glAttachShader(this.programs, this.programv);
        glLinkProgram(this.programs);
        glValidateProgram(this.programs);
        
        this.vertexPath = null;
        this.fragmentPath = null;
        this.isInitialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}
 	
 	public int getUniform(String name) {
 		return glGetUniformLocation(this.programs, name);
 	}
 	
 	public int getAttribLocation(String aname) {
 		return GL20.glGetAttribLocation(this.programs, aname);
 	}
 	
 	public void bind() {
 		glUseProgram(this.programs);
 	}
 	
 	public void unbind() {
 		glUseProgram(0);
 	}
 	
 	public int createShader(String filename,int type) {
		String code = "";
		int id = glCreateShader(type);
		try {
			code = readShader(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		glShaderSource(id, code);
        glCompileShader(id);
        
        printLog(id);
        return id;
 	}
  
 	private String readShader(String filename) throws Exception {
 		String code = "",line;
 		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
 		while ((line = reader.readLine()) != null) {
 			code += line + "\n";
 		}
 		reader.close();
 		return code;
 	}
  
 	private void printLog(int id) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        glGetShader(id, GL_INFO_LOG_LENGTH, intBuffer);
 
        int length = intBuffer.get();
 
        if (length <= 1) {
            return;
        }
 
        ByteBuffer infoBuffer = BufferUtils.createByteBuffer(length);
        intBuffer.flip();
 
        glGetShaderInfoLog(id, intBuffer, infoBuffer);
 
        int actualLength = intBuffer.get();
        byte[] infoBytes = new byte[actualLength];
        infoBuffer.get(infoBytes);
        System.out.print(new String(infoBytes));
        System.out.println(glIsShader(id));
 	}
 	
 	@Override
    public void finalize() {
        glDeleteShader(programs);
        programs = 0;
 
        glDeleteProgram(programf);
        programf = 0;
 
        glDeleteProgram(programv);
        programv = 0;
    }
}
