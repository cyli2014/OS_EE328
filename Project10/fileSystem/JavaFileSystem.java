package fileSystem;

/**
 * An implementation of the file system.
 *
 * Currently, all methods throw a RuntimeException.
 */

import java.util.*;
import java.io.*;
import disk.*;

public class JavaFileSystem implements FileSystem {
    // Set up the constants for the whence field in seek
    public static final int SEEK_SET    = 0;
    public static final int SEEK_CUR    = 1;
    public static final int SEEK_END    = 2;
    
    Disk myDisk;
    SuperBlock mySuperBlock;
    InodeBlock myInodeBlock;
    FileTable myFileTable;
    ArrayList<Integer> freeBlockList;

    public JavaFileSystem() {
    	myDisk = new Disk();
    	mySuperBlock = new SuperBlock();
    	myInodeBlock = new InodeBlock();
    	myFileTable = new FileTable();
    	freeBlockList = new ArrayList<Integer>(); //connect all the free blocks
    	//throw new RuntimeException("not implemented");
    }

    // Initialize the the disk to a state representing an empty file-system.
    // Fill in the super block, mark all inodes as "unused", and link
    // all data blocks into the free list.
    public int formatDisk(int size, int iSize) {
    	mySuperBlock.size = size; //the number of disk blocks in the file system
    	mySuperBlock.iSize = iSize; //the number of inode blocks
    	mySuperBlock.freeList = iSize + 1; //The first block of the free list
    	for(int j = 0; j < myDisk.NUM_BLOCKS - iSize - 1; j++) { //initial the free list
    		freeBlockList.add(iSize + 1 + j);
    	}
    	myDisk.write(0, mySuperBlock);
    	return 0;
        //throw new RuntimeException("not implemented");
    } // formatDisk

    // Close all files and shut down the simulated disk.
    public int shutdown() {
    	myDisk.write(0, mySuperBlock); //write back to disk
    	for(int j = 0; j <= 20; j++) { //clear all the states
    		if(myFileTable.bitmap[j] != 0)
    			myFileTable.free(j);
    	}
    	myDisk.stop(); //shut down
    	return 0;
        //throw new RuntimeException("not implemented");
    } // shutdown

    // Create a new (empty) file and return a file descriptor.
    public int create() {
    	int iN = myFileTable.allocate();
    	//myFileTable.bitmap[iN] = 1;
    	Inode fileInode = new Inode();
    	fileInode.flags = 1;
    	fileInode.fileSize = 0;
    	for(int j = 0; j < 13; j++)
    		fileInode.pointer[j] = 0;
    	myFileTable.add(fileInode, iN + 1, iN);
    	return iN;
        //throw new RuntimeException("not implemented");
    } // create

    // Return the inumber of an open file
    public int inumber(int fd) {
        return myFileTable.fdArray[fd].getInumber();
    	//throw new RuntimeException("not implemented");
    }

    // Open an existing file identified by its inumber
    public int open(int iNumber) {
        return myFileTable.getFDfromInumber(iNumber);
    	//throw new RuntimeException("not implemented");
    } // open

    // Read up to buffer.length bytes from the open file indicated by fd,
    // starting at the current seek pointer, and update the seek pointer.
    // Return the number of bytes read, which may be less than buffer.length
    // if the seek pointer is near the current end of the file.
    // In particular, return 0 if the seek pointer is greater than or
    // equal to the size of the file.
    public int read(int fd, byte[] buffer) {
        Inode fileInode = myFileTable.getInode(fd);
        int size = 0, m = 0;
    	byte[] buffertmp = new byte[myDisk.BLOCK_SIZE]; 
        for(int j = 0; j < 10; j++) {
        	if(fileInode.pointer[j] == 0) continue;
        	myDisk.read(fileInode.pointer[j], buffertmp);
        	for(int i = 0; m < buffer.length && i < myDisk.BLOCK_SIZE; m++, i++)
        		buffer[m] = buffertmp[i];
        	size++;
        	if(m == buffer.length) return buffer.length;
        }
        if(fileInode.pointer[10] != 0) {
        	if(m == buffer.length) return buffer.length;
        }
        if(fileInode.pointer[11] != 0) {
        	if(m == buffer.length) return buffer.length;
        }
        if(fileInode.pointer[12] != 0) {
        	if(m == buffer.length) return buffer.length;
        }
        return size * myDisk.BLOCK_SIZE;
    	//throw new RuntimeException("not implemented");
    } // read

    // Transfer buffer.length bytes from the buffer to the file, starting
    // at the current seek pointer, and add buffer.length to the seek pointer.
    public int write(int fd, byte[] buffer) {
        Inode fileInode = myFileTable.getInode(fd);
        int m = 0, size = 0;
    	byte[] buffertmp = new byte[myDisk.BLOCK_SIZE]; 
        for(int j = 0; j < 10; j++) {
        	if(fileInode.pointer[j] != 0) continue;
        	fileInode.pointer[j] = mySuperBlock.freeList;
        	freeBlockList.remove(mySuperBlock.freeList);
        	mySuperBlock.freeList = freeBlockList.get(0);
        	for(int i = 0; i < myDisk.BLOCK_SIZE && m < buffer.length; i++, m++)
        		buffertmp[i] = buffer[m];
        	myDisk.write(fileInode.pointer[j], buffertmp);
        	size++;
        	if(m == buffer.length) {
        		fileInode.fileSize = buffer.length;
        		myFileTable.fdArray[fd].setSeekPointer(buffer.length);
        		return buffer.length;
        	}
        }
        if(fileInode.pointer[10] != 0) {
        	if(m == buffer.length) {
        		fileInode.fileSize = buffer.length;
        		myFileTable.fdArray[fd].setSeekPointer(buffer.length);
        		return buffer.length;
        	}
        }
        if(fileInode.pointer[11] != 0) {
        	if(m == buffer.length) {
        		fileInode.fileSize = buffer.length;
        		myFileTable.fdArray[fd].setSeekPointer(buffer.length);
        		return buffer.length;
        	}
        }
        if(fileInode.pointer[12] != 0) {
        	if(m == buffer.length) {
        		fileInode.fileSize = buffer.length;
        		myFileTable.fdArray[fd].setSeekPointer(buffer.length);
        		return buffer.length;
        	}
        }
        fileInode.fileSize = size * myDisk.BLOCK_SIZE;
		myFileTable.fdArray[fd].setSeekPointer(size * myDisk.BLOCK_SIZE);
        return size * myDisk.BLOCK_SIZE;
    	//throw new RuntimeException("not implemented");
    } // write

    // Update the seek pointer by offset, according to whence.
    // Return the new value of the seek pointer.
    // If the new seek pointer would be negative, leave it unchanged
    // and return -1.
    public int seek(int fd, int offset, int whence) {
        Inode fileInode = myFileTable.getInode(fd);
    	switch(whence) {
    		case SEEK_SET:
    			if(offset < 0) return -1;
    			myFileTable.fdArray[fd].setSeekPointer(offset);
    			return offset;
    		case SEEK_CUR:
    			if(myFileTable.fdArray[fd].getSeekPointer() + offset < 0) return -1;
    			myFileTable.fdArray[fd].setSeekPointer(myFileTable.fdArray[fd].getSeekPointer() + offset);
    			return myFileTable.fdArray[fd].getSeekPointer() + offset;
    		case SEEK_END:
    			if(fileInode.fileSize + offset < 0) return -1;
    			myFileTable.fdArray[fd].setSeekPointer(fileInode.fileSize + offset);
    			return fileInode.fileSize + offset;
    		default: return -2;
    	}
    	//throw new RuntimeException("not implemented");
    } // seek

    // Write the inode back to disk and free the file table entry
    public int close(int fd) {
    	myDisk.write(1, myInodeBlock);
    	myFileTable.bitmap[fd] = 0;
    	return 0;
        //throw new RuntimeException("not implemented");
    } // close

    // Delete the file with the given inumber, freeing all of its blocks.
    public int delete(int iNumber) {
    	int iN = myFileTable.getFDfromInumber(iNumber);
    	myFileTable.bitmap[iN] = 0;
        Inode fileInode = myFileTable.getInode(iN);
        for(int j = 0; j < 10; j++) {
        	if(fileInode.pointer[j] == 0) continue;
        	freeBlockList.add(fileInode.pointer[j]);
        	fileInode.pointer[j] = 0;
        }
        if(fileInode.pointer[10] != 0) {
        	
        }
        if(fileInode.pointer[11] != 0) {
        	
        }
        if(fileInode.pointer[12] != 0) {
        	
        }
        return 0;
    	//throw new RuntimeException("not implemented");
    } // delete

    public String toString() {
        return "JAVAFileSystem";
    	//throw new RuntimeException("not implemented");
    }
}
