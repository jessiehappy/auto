/**
 * 
 */
package auto.datamodel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface IWritable {
    
    public void writeFields(DataOutput out) throws IOException;
    
    public void readFields(DataInput in) throws IOException;

}
