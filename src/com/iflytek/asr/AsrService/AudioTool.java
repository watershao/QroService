package com.iflytek.asr.AsrService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;

public class AudioTool {
	 // 这里得到可播放的音频文件  
	/**
	 * 将裸音频转换成可播放的音频文件
	 * @param inFilename 裸音频文件路径
	 * @param outFilename 可播放音频文件路径
	 */
    public static void copyWaveFile(String inFilename, String outFilename) {  
        FileInputStream in = null;  
        FileOutputStream out = null;  
        long totalAudioLen = 0;  
        long totalDataLen = totalAudioLen + 36;  
        long longSampleRate = 16000;  
     // 设置音频的录制的声道CHANNEL_IN_STEREO 为双声道，CHANNEL_CONFIGURATION_MONO 为单声道  
//        int channels = AudioFormat.CHANNEL_CONFIGURATION_MONO;  
        int channels = 1;  
//        long byteRate = 16 * longSampleRate * channels / 8;  
        long byteRate = longSampleRate * 2*channels;  
        byte[] data = new byte[20480];  
        try {  
            in = new FileInputStream(inFilename);  
            out = new FileOutputStream(outFilename);  
            totalAudioLen = in.getChannel().size();  
            totalDataLen = totalAudioLen + 36;  
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,  
                    longSampleRate, channels, byteRate);  
            while (in.read(data) != -1) {  
                out.write(data);  
            }  
            in.close();  
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    
    /** 
     * 这里提供一个头信息。插入这些信息就可以得到可以播放的文件。 
     * 为我为啥插入这44个字节，这个还真没深入研究，不过你随便打开一个wav 
     * 音频的文件，可以发现前面的头文件可以说基本一样哦。每种格式的文件都有 
     * 自己特有的头文件。 
     * @param out 文件输出流
     * @param totalAudioLen 块长度
     * @param totalDataLen 总长度 整个wav文件大小减去ID和Size所占用的字节数 即 fileLen -8
     * @param longSampleRate 采样频率 
     * @param channels 声道数目
     * @param byteRate 每秒所需字节数=采样频率*块对齐字节
     * @throws IOException
     */
    private static void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,  
            long totalDataLen, long longSampleRate, int channels, long byteRate)  
            throws IOException {  
        byte[] header = new byte[44];  
        header[0] = 'R'; // RIFF/WAVE header  
        header[1] = 'I';  
        header[2] = 'F';  
        header[3] = 'F';  
        header[4] = (byte) (totalDataLen & 0xff);  // 总长度 整个wav文件大小减去ID和Size所占用的字节数
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);  
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);  
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);  
        header[8] = 'W';  
        header[9] = 'A';  
        header[10] = 'V';  
        header[11] = 'E';  
        header[12] = 'f'; // 'fmt ' chunk  
        header[13] = 'm';  
        header[14] = 't';  
        header[15] = ' ';  
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk  // 块长度, 数值为16或18，18则最后有附加信息
        header[17] = 0;  
        header[18] = 0;  
        header[19] = 0;  
        header[20] = 1; // format = 1  // 编码方式, 一般为0x0001
        header[21] = 0;  
        header[22] = (byte) channels;  // 声道数目，1--单声道；2--双声道 
        header[23] = 0;  
        header[24] = (byte) (longSampleRate & 0xff); // 采样频率 
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);  
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);  
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);  
        header[28] = (byte) (byteRate & 0xff);  // 每秒所需字节数=采样频率*块对齐字节
        header[29] = (byte) ((byteRate >> 8) & 0xff);  
        header[30] = (byte) ((byteRate >> 16) & 0xff);  
        header[31] = (byte) ((byteRate >> 24) & 0xff);  
        header[32] = (byte) (2 * channels); // block align  数据块对齐单位(每个采样需要的字节数)// 数据对齐字节=每个样本字节数*声道数目
        header[33] = 0;  
        header[34] = 16; // bits per sample   每个采样需要的bit数 // 样本宽度
        header[35] = 0;  
        header[36] = 'd';  
        header[37] = 'a';  
        header[38] = 't';  
        header[39] = 'a';  
        header[40] = (byte) (totalAudioLen & 0xff);  // 块长度
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);  
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);  
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);  
        out.write(header, 0, 44);  
    }  
}
