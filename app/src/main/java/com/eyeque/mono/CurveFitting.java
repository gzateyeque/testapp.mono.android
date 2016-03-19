package com.eyeque.mono;
/**
 * Created by georgez on 2/24/16.
 */

public class CurveFitting
{
    /*
	public static void main(String[] args) throws Exception
	{
        // results will be spherical -3, cylindrical -6 and axis 30.0
        double[] powers  = {-7.497240598,-4.498620933,-8.999996195,-4.498620933, -7.505513728,-3.0};
        
        // 3, -3 0
        double[] powers1 = {0,0.75,0.75,3,2.25,2.25};
        
        // 5, -3 150
        double[] powers2 = {2.75,4.25,2,4.25,2.75,5};
        
        double[] angles  = {90.0,60.0,120.0,0,150.0,30.0};
        
        double[] results = curveFitting(angles,powers,6);
        
        System.out.println("-3   spherical:  "+results[0]);
        System.out.println("-6   cylinrical: "+results[1]);
        System.out.println("30.0 axis:       "+results[2]);
        
        results = curveFitting(angles,powers1,6);
        
        System.out.println("  3  spherical1:  "+results[0]);
        System.out.println(" -3 cylinrical1: "+results[1]);
        System.out.println("  0 axis1:       "+results[2]);
    
        results = curveFitting(angles,powers2,6);
        
        System.out.println("  5  spherical2:  "+results[0]);
        System.out.println(" -3  cylinrical2: "+results[1]);
        System.out.println(" 150 axis2:       "+results[2]);
    
    }
    */

    public double[] curveFitting(double[]  angl, double[]  P, int nm)
    {
        double M_PI=Math.PI;
        
        double coss2=0,coss=0,cossp=0,sph=0,cyl=0,sph2=0,cyl2=0;
        double   kkk=1000;
        double   kk=0;
        int   axis=0;
        double sum=0;
        for(int ii=0;ii<nm;ii++)
        {
            sum+=P[ii];
        }
        
        for(float i=0;i<180;i++)
        {
            kk=0;
            coss2=0;
            coss=0;
            cossp=0;
            for(int ii=0;ii<nm;ii++)
            {
                coss+=Math.cos(2*(angl[ii]-i)/180.0*M_PI);
                coss2+=(Math.cos(2*(angl[ii]-i)/180.0*M_PI))*(Math.cos(2*(angl[ii]-i)/180.0*M_PI));
                cossp+=Math.cos(2*(angl[ii]-i)/180.0*M_PI)*P[ii];
            }
            
            double dNM=(double) nm;
            cyl=(cossp-coss*sum/dNM)/(coss2-coss*coss/dNM);
            sph=sum/nm-cyl*coss/dNM+cyl;
            cyl=-2*cyl;
            if(cyl<=0)
            {
                for(int ii=0;ii<nm;ii++)
                    kk+=(sph+cyl*Math.sin((i-angl[ii])/180.0*M_PI)*Math.sin((i-angl[ii])/180.0*M_PI)-P[ii])*(sph+cyl*Math.sin((i-angl[ii])/180.0*M_PI)*Math.sin((i-angl[ii])/180.0*M_PI)-P[ii]);
                if (kk<=kkk)
                {
                    kkk=kk;
                    sph2=sph;
                    cyl2=cyl;
                    axis=(int)i;
                }
            }
        }
        
        double [] retDouble = new double[3];
        
        retDouble[0]=sph;
        retDouble[1]=cyl;
        retDouble[2]=axis;
        
        return retDouble;
    }
    
    public double[] curveFitting1(double []  x, double []  y)
    {
        int N = x.length;
        double kkk=0;
        double kk=0;
        int   axis=0;
        
        double coss2=0,coss=0,cossp=0,sph=0,cyl=0;
        
        double sum=0;
        for(int i=0;i<N;i++)
        {
            sum+=y[i];
        }
        
        
        for(int i=0;i<180;i++)
        {
            kk=0;
            for(int ii=0;ii<N;ii++)
            {
                kk+=(y[ii]-sum/N)*Math.cos(2*(x[ii]-i)/180*Math.PI);
            }
            
            if (kk>=kkk)
            {
                kkk=kk;
                axis=i;
            }
        }
        
        for(int ii=0;ii<N;ii++)
        {
            coss+=Math.cos(2*(x[ii]-axis)/180*Math.PI);
            coss2+=(Math.cos(2*(x[ii]-axis)/180*Math.PI))*(Math.cos(2*(x[ii]-axis)/180*Math.PI));
            cossp+=Math.cos(2*(x[ii]-axis)/180*Math.PI)*y[ii];
        }
        
        cyl=(cossp-coss*sum/N)/(coss2-coss*coss/N);
        sph=sum/N-cyl*coss/N+cyl;
        cyl=-2*cyl;
        
        double [] retDouble = new double[3];
        
        retDouble[0]=sph;
        retDouble[1]=cyl;
        retDouble[2]=axis;
        
        return retDouble;
    }
}