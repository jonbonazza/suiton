package com.nebula2d.editor.framework.assets;

import com.nebula2d.editor.util.FullBufferedReader;
import com.nebula2d.editor.util.FullBufferedWriter;

import java.io.*;

public class Script extends Asset {

    protected String content;

    protected void init() {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            String tmp = "";
            while((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }

            content = sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file at path: " + path);
        } catch (IOException e) {
            System.out.println("Failed to read file at path: " + path);
        }
    }

    public Script(String path) {
        super(path);
        init();
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public void load(FullBufferedReader fr) throws IOException {
        //Noop!
    }
}