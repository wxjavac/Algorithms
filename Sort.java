package com.sort;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/*
*写一些静态的排序算法,插入排序,选择排序,归并排序,希尔排序,快速排序,还有他们的一些改进
*默认都是递增排序
 */
public class Sort {
    public static final int YMAX=1000;
    private static Comparable[] aux;
    public static int count=0;
    private static final int M=5;//规定使用小数组的规模
    //用于比较的函数
    public static boolean less(Comparable v, Comparable n){
        return v.compareTo(n)<0;//如果v大于n返回false,反之返回true
    }
    //用于交换两个数的函数
    private static void each(Comparable a[],int i,int j){
        Comparable t=a[i];
        a[i]=a[j];
        a[j]=t;
    }
    //判断是否排序完毕
    public static boolean isSorted(Comparable a[]){
        for (int i=0;i<a.length-1;i++){
            if(less(a[i+1],a[i])){
                return false;
            }
        }
        return true;
    }
    //显示排序结果
    public static void show(Comparable a[]){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    private static void show(Comparable[] a, int low, int high) {
        for(int i=low;i<=high;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    //可视化显示数组
    public static void showSaw(Comparable[] a){
        int N=a.length;
        StdDraw.setYscale(0,YMAX);
        for(int i=0;i<N;i++){
            double x=1.0*i/N;
            double y=(int)a[i]/2.0;
            double rw=0.5/N;
            double rh=(int)a[i]/2.0;
            StdDraw.filledRectangle(x,y,rw,rh);
        }
    }
    public static void showSaw(Comparable[] a,int index){
        int N=a.length;
        StdDraw.setYscale(0,YMAX);
        for(int i=0;i<N;i++){
            double x=1.0*i/N;
            double y=(int)a[i]/2.0;
            double rw=0.5/N;
            double rh=(int)a[i]/2.0;

            if(i==index){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledRectangle(x,y,rw,rh);
                StdDraw.setPenColor(StdDraw.BLACK);
            }
            else{
                StdDraw.filledRectangle(x,y,rw,rh);
            }
        }
    }
    //选择排序
    public static void selectSort(Comparable a[]){
        int N=a.length;
        int min;
        for(int i=0;i<N;i++){
            min=i;
            for(int j=i+1;j<N;j++){//从后面数组中找到那个最小的
                if(less(a[j],a[min])){
                    min=j;
                }
            }
            showSaw(a,min);
            System.out.println(min);
            each(a,min,i);//把最小的放到前面数组的最后
            showSaw(a,i);
            StdDraw.pause(500);
            StdDraw.clear();
        }
    }
    //插入排序
    public static void insertSort(Comparable a[]){
        int N=a.length;
        for(int i=1;i<N;i++){
            //for(int j=i;j>0&&less(a[j],a[j-1]);j--){
            //   // each(a,j,j-1);//这个是一直向前交换,把小的放到前面去
            //
            //}
            //有一种改良方法是不用交换,要把较大的数向后移动
            Comparable M=a[i];//保存当前位置数据
            int j=i ;
            while(j>0&&less(M,a[j-1])){//确定需要插入的位置
                a[j]=a[j-1];            //把较大的数据向后移动
                j--;
            }

            a[j]=M;//将要插入的数据放入
            //showSaw(a,i);
            //showSaw(a,j);
            //StdDraw.pause(1000);
            //StdDraw.clear();
        }
    }
    private static void insertSort(Comparable[] a, int low, int high) {
        show(a,low,high);
        for(int i=low+1;i<=high;i++){

            //for(int j=i;j>0&&less(a[j],a[j-1]);j--){
            //   // each(a,j,j-1);//这个是一直向前交换,把小的放到前面去
            //
            //}
            //有一种改良方法是不用交换,要把较大的数向后移动
            Comparable M=a[i];//保存当前位置数据
            int j=i ;
            while(j>low&&less(M,a[j-1])){//确定需要插入的位置
                a[j]=a[j-1];            //把较大的数据向后移动
                j--;
            }

            a[j]=M;//将要插入的数据放入
            //showSaw(a,i);
            //showSaw(a,j);
            //StdDraw.pause(1000);
            //StdDraw.clear();
        }
        show(a,low,high);
    }



    //希尔排序
    public static void shellSort(Comparable a[]){
        int h=1;
        int N=a.length;
        while(h<N/3)h=h*3+1;//设置h的最大取值,1,4,13,,,,,,
        while(h>=1){
            for(int i=h;i<N;i++){
                for(int j=i;j>=h&&less(a[j],a[j-h]);j-=h){
                    each(a,j,j-h);
                }
                showSaw(a);
                StdDraw.pause(500);
                StdDraw.clear();
            }
            h/=3;
        }


    }
    //归并排序,递归调用,自上而下
    public static void mergeSort(Comparable a[]){
        aux=new Comparable[a.length];
        int low=0;
        int high=a.length-1;
        mergeSort(a,low,high);
    }
    private static void mergeSort(Comparable[] a, int low, int high) {
        if(high<=low+M){//这里的M规定了如果数组小到什么程度就使用插入排序
            insertSort(a,low,high);
            return;
        }else{
        int mid=(low+high)/2;
        mergeSort(a,low,mid);
        mergeSort(a,mid+1,high);
        merge(a,low,mid,high);
        }
    }



    //合并算法
    public static void merge(Comparable a[],int low,int mid,int high){
        int m=low;
        int n=mid+1;
        if(less(a[mid],a[mid+1]))return;//判断是否已经有序,如果有序则不再进行合并
        for(int i=low;i<=high;i++)aux[i]=a[i];
        count++;

        for(int i=low;i<=high;i++){

            if(m>mid) a[i]=aux[n++];
            else
                if(n>high) a[i]=aux[m++];
                else
                    if(less(aux[m],aux[n]))a[i]=aux[m++];
                    else a[i]=aux[n++];
        }
        count++;
    }
    //自底向上的归并排序
    public static void mergeButtom_up(Comparable a[]){
        int N=a.length;
        aux=new Comparable[N];
        for(int i=1;i<N;i=i+i){
            for(int lo=0;lo<N-i;lo=lo+i+i){
                merge(a,lo,lo+i-1,Math.min(lo+i+i-1,N-1));
            }
        }
    }
    //快速排序
    public static void quickSort(Comparable a[]){
        int N=a.length;//数组的长度
        int lo=0;
        int high=N-1;

        quickSort3bars(a,lo,high);


    }

    private static void quickSort(Comparable[] a, int lo, int high) {
        /*
        算法改进一:切换到插入排序
         */
        if (high <= lo + M) {
            insertSort(a, lo, high);//当数组规模小到一定程度时就切换到插入排序
            return;
        } else {
            int mid = partition(a, lo, high);
            quickSort(a, lo, mid - 1);//根据拆分值来进行分别的排序,如果每次拆分都能接近对半的话,效率最高
            quickSort(a, mid + 1, high);
        }
    }

    /**
     * 快速排序改进二----三取样切分改进
     * 就是再取切分元素的时候,从数组中抽取3个数,取他们的中位数作为切分元素
     * 这个只是在取切分值得时候取样然后取中位数,可以和改进一结合
     * @param a  需要排序的数组
     * @param lo 最小下标
     * @param high 最大下标
     * @return
     */
    private static void quickSort3bars(Comparable[] a, int lo, int high) {
        int n = high - lo + 1;
        if (high <= lo + M) {
            insertSort(a, lo, high);//当数组规模小到一定程度时就切换到插入排序
            return;
        } else {
            int m=median3(a,lo,lo+n/2,high);
            each(a,m,lo);
            int mid = partition(a, lo, high);
            quickSort3bars(a, lo, mid - 1);//根据拆分值来进行分别的排序,如果每次拆分都能接近对半的话,效率最高
            quickSort3bars(a, mid + 1, high);
        }
    }

    /**
     *取中位数
     */

    public static int median3(Comparable a[],int i,int j,int k){
        return less(a[i],a[j])?(less(a[i],a[k])?(less(a[j],a[k])?j:k):i):
                (less(a[j],a[k])?(less(a[i],a[k])?i:k):j);
    }
    //快速排序拆分数组
    public static int partition(Comparable a[],int lo,int high){
        Comparable value=a[lo];
        int i=lo;
        int j=high+1;
        while(true){
            while(less(a[++i],value))if(i==high)break;
            while(less(value,a[--j]))if(j==lo)break;
            if(j<=i)break;
            each(a,j,i);
        }
        System.out.println(i+" "+j);
        each(a,j,lo);//要知道返回的j,在i,j,游标移动的时候,i,j有可能相等,也有可能j<i,j一直代表左半边的最右边
        return j;//返回的是拆分中间值
    }

    /**
     * 快速排序改进三----三向切分
     * @param a
     */
    public static void quickSort3way(Comparable a[]){
        StdRandom.shuffle(a);
        quickSort3way(a,0,a.length-1);
    }

    private static void quickSort3way(Comparable[] a, int lo, int high) {
        if(high<=lo)return;

        int lt=lo;  //设置左侧游标
        int jt=high;    //设置右侧游标
        int i=lo+1; //用i进行数组的遍历,当i遇到右侧游标时说明已经没有再比切分值大的了
        Comparable v=a[lo];//切分值
        while(i<=jt){
            int m=v.compareTo(a[i]);//比较切分值和当前a[i]的值
            if(m<0)each(a,i,jt--);//当m<0说明切分值小于当前的值,要把当前值放到切分值的右边,让他与jt右边游标进行交换,i不变,进行下一次的比较
            else
                if(m>0)each(a,i++,lt++);//如果m>0,说明切分值小于当前值,当前值和左边游标交换,因为在左边的就已经是小于切分值,所以i++,不用等待下一次比较
                else
                    i++;//如果相等,则向右继续遍历,最终使lo->lt间都是小于切分值,lt-jt都等于切分值,jt-high都大于切分值
        }

        quickSort3way(a,lo,lt-1);
        quickSort3way(a,jt+1,high);

    }

    /**
     * 实现一个非递归的快速排序
     */
    public   static void quickSortwithoutRecursion(Comparable []a){
        //定义一个数据类型存放lo,high
        class Node{
            private int lo;
            private int high;

            public int getLo() {
                return lo;
            }

            public int getHigh() {
                return high;
            }

            public Node(int lo, int high) {
                this.lo = lo;
                this.high = high;
            }
        }

        Stack<Node> s=new Stack<>();
        s.push(new Node(0,a.length-1));
        while(!s.isEmpty()){
            Node p=s.pop();
            if(p.getHigh()<=p.getLo())continue;
            int mid=partition(a,p.getLo(),p.getHigh());
            s.push(new Node(p.getLo(),mid-1));
            s.push(new Node(mid+1,p.getHigh()));
        }
    }


}
