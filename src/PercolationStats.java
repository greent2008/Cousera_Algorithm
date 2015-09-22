/**
 * Created by jianengxi on 15-9-22.
 */
import edu.princeton.cs.algs4.StdStats;
import static java.lang.Math.random;

public class PercolationStats {
    private Percolation experiment;
    private int times;  //实验次数
    private double[] threshold;   //实验结果,放在double数组里
    public PercolationStats(int N, int T){
        if (N < 1 || T < 1)
        {
            throw new IllegalArgumentException("argument out of bound");
        }
        times = T;
        threshold = new double[times];
        int count =0;
        for (int i = 0; i < times; i++){
            experiment = new Percolation(N);
            while(!experiment.percolates())
            {
                int x = (int)(random()*N) + 1;
                int y = (int)(random()*N) + 1;
                if (!experiment.isOpen(x,y))
                {
                    experiment.open(x,y);
                    count++;
                }
            }
            threshold[i] = (double)count / (double)(N*N);
            count = 0;
            experiment.reset();
        }

    }     // perform T independent experiments on an N-by-N grid
    public double mean(){
        return StdStats.mean(threshold);
    }                      // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(threshold);
    }                    // sample standard deviation of percolation threshold
    public double confidenceLo(){
        double Lo = this.mean() - 1.96*this.stddev() / Math.sqrt(times);
        return  Lo;
    }              // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        double Hi = this.mean() + 1.96*this.stddev() / Math.sqrt(times);
        return Hi;
    }              // high endpoint of 95% confidence interval

    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(N, T);
        System.out.println("mean                    = "+p.mean());
        System.out.println("stddev                   = "+p.stddev());
        System.out.println("95% confidence interval = "+p.confidenceLo() + ", " + p.confidenceHi());
    }    // test client (described below)
}
