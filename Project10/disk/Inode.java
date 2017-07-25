package disk;

/**
 * TO DO: This class has to add getter/setter methods for the data elements
 * to allow the elements to be declared as private
 *
 */
public class Inode {
	public final static int SIZE = 64;	// size in bytes
	public int flags;
	int owner;
	public int fileSize;
	public int pointer[] = new int[13];

	public String toString() {
		String s = "[Flags: " + flags
		+ "  Size: " + fileSize + "  ";
		for (int i = 0; i < 13; i++) 
			s += "|" + pointer[i];
		return s + "]";
	}
}
