package com.openatc.comm.ocp;
import static com.openatc.comm.ocp.CosntDataDefine.*;
/**
 * @ClassName: DataPackUpPack
 * @Description: TODO
 * @author  liangting
 * @date 2019年10月30日 上午10:16:06
 *
// */
public class DataPackUpPack{

    public DataPackUpPack(){
    }

    /**
     * @param  dataSchedule 数据表，pDest 装帧结果
     * @return dwDstCount 装帧结果数据长度
     * @Title: PackBuff
     * @Description: 根据国标对数据表进行装帧
     */
    public int packBuff(byte[] dataSchedule, byte[] pDest)
    {
        int dwSrcCount = dataSchedule.length;
        pDest[0] = CB_HEADTAIL;
        int dwDstCount = 1;
        for (int i = 0;i < dwSrcCount;i++)
        {
            switch (dataSchedule[i])
            {
                case CB_HEADTAIL:
                {
                    pDest[dwDstCount++] = CB_ESCAPE1;
                    pDest[dwDstCount++] = CB_ESCAPE2;
                }
                break;
                case CB_ESCAPE1:
                {
                    pDest[dwDstCount++] = CB_ESCAPE1;
                    pDest[dwDstCount++] = CB_ESCAPE3;
                }
                break;
                default:
                {
                    pDest[dwDstCount++] = dataSchedule[i];
                }
                break;
            }
        }
        pDest[dwDstCount++] = 0x00;//数据校验位默认为0x00
        pDest[dwDstCount++] = CB_HEADTAIL;
        return dwDstCount;//打包后数据的大小
    }

    /**
     * @param  pBuff 数据帧，dataSchedule 提取出的数据表
     * @return dwCount 数据表长度  返回为0时表示数据表数据内容格式错误，
     * @Title: UpPackBuff
     * @Description: 从数据帧中提取数据表
     */
    public int upPackBuff(byte[] pBuff, byte[] dataSchedule) {
        int head = 0;
        int tail = 1;
        int dwCount = 0;
        int dataBufferSize = pBuff.length;
        //查找帧结构的结束标志符位置
        if (pBuff[head] == CB_HEADTAIL) {
            while ((pBuff[tail] != CB_HEADTAIL) && (tail != head)) {
                tail = (tail + 1) % dataBufferSize;
            }
        } else if (pBuff[head] != CB_HEADTAIL) {
            return dwCount;//数据格式错误
        }
        if ((tail == head) ||(tail==1)) {
            return dwCount;//无数据
        }
        if (tail != head) {
            byte[] cmBuff = new byte[tail-2];
            //取出数据表内容
            for (int i = 1; i < tail - 1; i++) {
                cmBuff[i-1] = pBuff[i];
            }
            for (int j = 0; j < cmBuff.length; j++) {
                byte m_dataSchedule = cmBuff[j];
                if (m_dataSchedule == CB_ESCAPE1) {
                    byte dataESC = cmBuff[j + 1];
                    switch (dataESC) {
                        case CB_ESCAPE2:
                            dataSchedule[dwCount++] = CB_HEADTAIL;
                            j++;
                            break;
                        case CB_ESCAPE3:
                            dataSchedule[dwCount++] = CB_ESCAPE1;
                            j++;
                            break;
                        default :
                            dwCount = 0;
                            return dwCount;
                    }
                }
                else{
                    dataSchedule[dwCount++] = m_dataSchedule;
                }
            }
        }
         return dwCount;
    }
}