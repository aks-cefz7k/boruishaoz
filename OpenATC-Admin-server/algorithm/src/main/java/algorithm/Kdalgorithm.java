/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package algorithm;

import  lombok.Data;

@Data
public class Kdalgorithm {

    private int precycle;  //优化前周期
    private int aftcycle;  //优化后周期
    private double trafficintensity;  //关键路口交通强度值
    private int m_volume;  //关键路口关键车道平均交通流量
    private int m_saturation;  //关键路口关键车道饱和度

    Greenwave gwup=new Greenwave();  //优化后上行带宽参数
    Greenwave gwdown=new Greenwave();//优化后下行带宽参数


    /**   参考 scats模型
     *   根据路口流量，优化周期；
     *
     *   volume  流量
     *   min1    最小周期参数min1
     *   min2    最小流量参数min2
     *   volume  流量
     *   min1    最小流量参数min1
     *   min2    最小流量参数min2
     */

    public int cycleOpt(int volume,int saturation,int cyclemin1,int cyclealt1,int cyclealt2,int cyclestr,int cyclemax)
    {
        m_volume=volume;
        m_saturation=saturation;

        if(m_volume<=4)  // 3~5
            aftcycle=cyclemin1;
        else if(m_volume<=8) // 6~9
            aftcycle=cyclealt1;
        else if(m_saturation<=85) // 80~90
            aftcycle=cyclealt2;
        else if(m_saturation<=100) // 90~100
        {
            aftcycle=cyclealt2+cyclestr;  //待计算
        }
        else if(m_saturation<=120) // 110~140
        {
            aftcycle = cyclestr+cyclemax;  //待计算
        }
        else
            aftcycle=cyclemax;

        //和前一周期相比，差别不能太大

        return aftcycle;
    }
    /**
     *   根据各路段长度、速度、 求解单向绿波相位差，带宽参数；
     *   intsoffset[];  各路口相位差
     */

    public int[] offsetOpt(String type, double intslenth[], double intsvel, int intssplit[])
    {
        double vel=0.0;//速度m/s
        vel=intsvel/3.6;
        int intsoffset[]; //各路口相位差
        intsoffset = new int[intslenth.length];
        int intstraltime[]; //路段平均行程时间   假入10个路口，10个平均行程时间
        intstraltime = new int[intslenth.length];

        for(int i=0;i<intslenth.length;i++)
        {
            intsoffset[i]=0;

        }
        for(int i=0;i<(intslenth.length);i++)
        {
            intstraltime[i]=0;
        }

        //计算每一个路段所需行程时间
        for(int i=0;i<intslenth.length;i++)
            intstraltime[i]=(int)(intslenth[i]/vel);

        //相位差计算
        for(int i=0;i<intslenth.length;i++)
        {
            if(intstraltime[i]>=aftcycle)
                intsoffset[i]=intstraltime[i]%aftcycle;
            else
                intsoffset[i]=intstraltime[i];
        }

        //重新修正为相对第一个路口的相位差
        for(int i=0;i<intslenth.length;i++)
        {
            if(i==0)
                intsoffset[i]=0;
            else
                intsoffset[i]=(intsoffset[i]+intsoffset[i-1])%aftcycle;
        }

        //计算带宽参数 单向的话等于最小的绿信比的值
        int width=aftcycle;
        for(int i=0;i<intssplit.length;i++)
        {
            if(width>intssplit[i]) //在各路口绿信比内
                width=intssplit[i];
        }

        if(type.equals("up"))   //up, down
        {
            gwup.start=0;
            gwup.type="up";
            gwup.width=width;
            gwup.speed=intsvel;
            gwup.vehicle=10;


        }else if(type.equals("down"))
        {
            //调整相位差
            for(int i=0;i<intslenth.length;i++)
            {
                if (intsoffset[i]!=0)
                    intsoffset[i]=aftcycle-intsoffset[i];
            }

            gwdown.start=0;
            gwdown.type="down";
            gwdown.width=width;
            gwdown.speed=intsvel;
            gwdown.vehicle=10;
        }

        return  intsoffset;
    }
    /**
     *   根据各路段长度、速度、 图解法 求解双向绿波相位差，带宽参数；
     */
    public int[] offsetByBiDirection(double intslenth[], double intsvelup, double intsveldown, int intssplit[])
    {
        //1.确定和第一个路口同步式协调or交互式协调

//        int[] intsmode; //各路口协调方式，0 同步式，cycle/2 交互式
        int[] intsoffset; //各路口相位差
//        intsmode = new int[intslenth.length];
        intsoffset = new int[intslenth.length];

        int[] intstraltime; //路段平均行程时间   假入10个路口，10个平均行程时间, 除以周期后的相对时间,分正反向计算
        intstraltime = new int[intslenth.length];

//        for(int i=0;i<intslenth.length;i++)
//        {
//            intsmode[i]=0;
//        }
        for(int i=0;i<(intslenth.length-1);i++)
        {
            intstraltime[i]=0;
        }

        gwup.start=0;
        gwup.type="up";
        gwup.width=0;
        gwup.speed=intsvelup;
        gwup.vehicle=10;

        gwdown.start=0;
        gwdown.type="down";
        gwdown.width=0;
        gwdown.speed=intsveldown;
        gwdown.vehicle=10;

        //计算每一个路段所需行程时间

//        intsveldown=intsveldown*1000/3600;
        intsvelup=intsvelup*1000/3600;

        int s=0; //加和得到该路口同前面所有路口的距离;
        for(int i=0;i<intslenth.length;i++)
        {
            if(i==0)
                intstraltime[0]=0;//基准路口
            else{
                s+=intslenth[i];
                intstraltime[i]=(int)(s/intsvelup);
            }
        }

        for(int i=0;i<intslenth.length;i++)
        {
            int m_temp=intstraltime[i]%aftcycle;
            if(m_temp<=(aftcycle/2))
            {
//                intsmode[i]=0; //0同步式，aftcycle/2交互式
                intsoffset[i]=0;
            }
            else
            {
//                intsmode[i]=aftcycle/2;
                intsoffset[i]=aftcycle/2;
            }
        }

        ///////////////////////////////////

        int width=0;

        //依此调整每个路口的相位差，最后得到整个绿波带的最大值
        for(int i=0;i<intslenth.length;i++)
        {

            int offset=intsoffset[i];

            for(int off=0;off<aftcycle;off++)
            {
                //求得i路口此相位差下绿波带宽
                int pt1=-1;
                int pt2=-1;
                int pt3=-1;
                int pt4=-1;
                intsoffset[i]=off;
                //正向第一个点
                for(int j=0;j<intslenth.length;j++)
                {
                    //判断点 intsoffset[j]，intsoffset[j]+intssplit[j]是不是绿波带的关键边界点。并统一到第一个路口对应坐标上。
                    int d1=(1000*aftcycle+intsoffset[j]-intstraltime[j])%aftcycle;

                    boolean ff1= isintsOffsetPointforward(1,d1,intsoffset,intstraltime,intssplit);
                    if(ff1)
                    {
                        pt1 = calFirstPoint(pt1, d1);
                        break;
                    }
                }
                //正向第er个点
                for(int j=0;j<intslenth.length;j++)
                {
                    //判断点 intsoffset[j]，intsoffset[j]+intssplit[j]是不是绿波带的关键边界点。并统一到第一个路口对应坐标上。
                    int d2=(1000*aftcycle+intsoffset[j]+intssplit[j]-intstraltime[j])%aftcycle;

                    boolean ff2= isintsOffsetPointforward(2,d2,intsoffset,intstraltime,intssplit);
                    if(ff2)
                    {
                        pt2= calTwoPoint(pt2,d2);
                        break;
                    }
                }

                int width1=pt2-pt1;

                //反向第一个点
                for(int j=0;j<intslenth.length;j++)
                {
                    int d1=(1000*aftcycle+intsoffset[j]+intstraltime[j])%aftcycle;

                    boolean ff1= isintsOffsetPointreverse(1,d1,intsoffset,intstraltime,intssplit);
                    if(ff1)
                    {
                        pt3= calFirstPoint(pt3,d1);
                        break;
                    }

                }

                //反向第二个点
                for(int j=0;j<intslenth.length;j++)
                {
                    int d2=(1000*aftcycle+intsoffset[j]+intssplit[j]+intstraltime[j])%aftcycle;
                    boolean ff2= isintsOffsetPointreverse(2,d2,intsoffset,intstraltime,intssplit);
                    if(ff2)
                    {
                        pt4= calTwoPoint(pt4,d2);
                        break;
                    }
                }

                int width2=pt4-pt3;

                if(width<(width1+width2))
                {
                    //记下相位差，正反向第一个路口初始值
                    offset=off;
                    width=width1+width2;
                    gwup.start=pt1;
                    gwup.width=width1;
                    gwdown.start=pt3;
                    gwdown.width=width2;
                }
            }

            intsoffset[i]=offset;

        }

        return intsoffset;
    }

    //是否正向端点
    //端点1必须经过的都是下端点；
    //端点2必须经过的都是上端点；
    public boolean isintsOffsetPointforward(int num, int dis, int intsoffset[], int intstraltime[], int intssplit[])
    {
        boolean flag=true;

        for(int k=0;k<intsoffset.length;k++)
        {
            int temp=(dis+intstraltime[k])%aftcycle;
            if((temp<intsoffset[k] && temp>=0) || temp>(intsoffset[k] +intssplit[k]) ||(num==1 && temp==(intsoffset[k] +intssplit[k])) ||(num==2 && temp==intsoffset[k]))
            {
                flag = false;
                break;
            }
        }

        return flag;
    }

    //是否反向端点
    public boolean isintsOffsetPointreverse(int num, int dis, int intsoffset[], int intstraltime[], int intssplit[])
    {
        boolean flag=true;

        for(int k=0;k<intsoffset.length;k++)
        {
            int temp=(1000*aftcycle+dis-intstraltime[k])%aftcycle;
            if((temp<intsoffset[k] && temp>=0) || temp>(intsoffset[k] +intssplit[k]) ||(num==1 && temp==(intsoffset[k] +intssplit[k])) ||(num==2 && temp==intsoffset[k]))
            {
                flag=false;
                break;
            }
        }

        return flag;
    }

    //重新计算端点1
    public int calFirstPoint(int pt1, int dis)
    {
        //判断第一个点是否存在
        if(pt1>=0)
        {
            if(dis<pt1)
                pt1=dis;
        }
        else
            pt1=dis;
        return  pt1;
    }

    //重新计算端点2
    public int calTwoPoint(int pt2, int dis)
    {
        //判断第er个点是否存在
        if(pt2>=0)
        {
            if(dis>pt2)
                pt2=dis;
        }
        else
            pt2=dis;
        return pt2;
    }
}
