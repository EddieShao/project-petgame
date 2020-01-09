package com.example.petgame.PetGameSettings;

import android.content.Context;

import com.example.petgame.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/** Reads and updates the settings_config properties file to update game settings. */
class SettingsFileManager {

    /** The properties of the settings. */
    private Properties properties;

    /** The Context containing the settings popup of this SettingsFileManager. */
    private Context context;

    /** Create a SettingsFileManager to manage the properties of the SETTINGS_CONFIG_FILE file. */
    SettingsFileManager(Context context) {
        // create default properties for the game (in case file does not exist)
        Properties defaultProps = new Properties();
        defaultProps.setProperty(Property.BACKGROUND.getProperty(), Background.BEDROOM.getBackground());
        defaultProps.setProperty(Property.HARD_MODE.getProperty(), "false");
        defaultProps.setProperty(Property.MUTED.getProperty(), "true");

        this.context = context;

        try {
            // create properties with default properties above
            Properties props = new Properties(defaultProps);
            // create an input stream pointing to the settings properties
            InputStream input = this.context.getResources().openRawResource(R.raw.settings_config);
            // create a reader for this input stream and load it into the Properties object
            InputStreamReader reader = new InputStreamReader(input);
            props.load(reader);
            // store this properties object
            this.properties = props;
            // close the reader
            reader.close();
        } catch (IOException e) {
            // config.properties file not found... log failure and use default properties instead
            e.printStackTrace();
        }
    }

    /**
     * Read the settings configurations from the SETTINGS_CONFIG_FILE file at the ...\files path.
     *
     * @return A length 3 String array containing current settings properties in the form
     * [background ID, hard mode boolean, muted boolean]
     */
    String[] readFromFile() {
        // obtain all settings configurations
        String background = this.properties.getProperty(Property.BACKGROUND.getProperty());
        String hardMode = this.properties.getProperty(Property.HARD_MODE.getProperty());
        String muted = this.properties.getProperty(Property.MUTED.getProperty());
        // return all settings configurations
        return new String[] {background, hardMode, muted};
    }

    /**
     * Write the settings config value with the given key to the SETTINGS_CONFIG_FILE file.
     *
     * @param property The property key to write the given value to.
     * @param value The boolean value to write to the given property
     */
    void writeToFile(Property property, Object value) {
        try {
            // create an output stream pointing to the file
            FileOutputStream output = this.context.openFileOutput("settings_config.properties", Context.MODE_PRIVATE);
            // create a writer to write to this output stream
            OutputStreamWriter writer = new OutputStreamWriter(output);
            // update the given property
            this.properties.setProperty(property.getProperty(), String.valueOf(value));
            // write the updates properties to the .properties file
            this.properties.store(writer, "update boolean settings");
            // close the writer
            writer.close();
        } catch (IOException e) {
            // file not found or properties field is invalid... log failure
            e.printStackTrace();
        }
    }

}
