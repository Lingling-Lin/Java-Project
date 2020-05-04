/* HullBuilder.java
   CSC 225 - Summer 2019

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 03/18/2019
   (Add your name/studentID/date here)
*/

import java.util.LinkedList;
import java.util.Arrays;
public class HullBuilder{
    Point2d[] H_a;
    Point2d[] U_a;
    Point2d[] L_a;
    //Point2d[] S;
    LinkedList<Point2d> H_l = new LinkedList<Point2d>();
    int h;

    public HullBuilder(){
        this.H_a = null;
        this.U_a = null;
        this.L_a = null;
        this.H_l = new LinkedList<Point2d>();
        //this.S = null;
        this.h = 0;
    }
    /* Add constructors as needed */

    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one).
    */
    public void addPoint(Point2d P){
        Point2d[] S = new Point2d[h+1];
        int i = binarySearch(H_a, P, 0, h-1);
        for (int j= 0; j<i; j++){
            S[j] = H_a[j];
        }
        S[i] = P;
        for (int k= i; k<h; k++){
            S[k+1] = H_a[k];
        }

        if (h < 2) {
            H_a = S;
            H_l = new LinkedList<Point2d>();
            U_a = new Point2d[h + 1];
            L_a = new Point2d[h + 1];
            for (int j = 0; j < h + 1; j++) {
                H_l.add(H_a[j]);
                U_a[j] = H_a[j];
                L_a[j] = H_a[j];
            }
            h++;
        }
        else {
            // for (int j = 0; j < Temp_a.length; j++) {
            //     //Point2d P = H.get(i);
            //     System.out.println(Temp_a[j].x + ", " + Temp_a[j].y);
            // }
            //System.out.println();
            LinkedList<Point2d> U = new LinkedList<Point2d>();
            LinkedList<Point2d> L = new LinkedList<Point2d>();
            U.add(S[0]);
            U.add(S[1]);
            L.add(S[0]);
            L.add(S[1]);
            Point2d a;
            Point2d b;
            for(int j= 2; j<h+1; j++){
                while (U.size()>=2){
                    a = U.get(U.size() - 2);
                    b = U.get(U.size() - 1);
                    if (Point2d.chirality(a, b, S[j]) > 0){
                        break;
                    }else{
                        U.removeLast();
                    }
                }
                U.add(S[j]);
            }
            for(int j= 2; j<h+1; j++){
                while (L.size()>=2){
                    a = L.get(L.size() - 2);
                    b = L.get(L.size() - 1);
                    if (Point2d.chirality(a, b, S[j]) < 0){
                        break;
                    }else{
                        L.removeLast();
                    }
                }
                L.add(S[j]);
            }
            H_l = new LinkedList<Point2d>();


            U_a = U.toArray(new Point2d[U.size()]);
            L_a = L.toArray(new Point2d[L.size()]);
            for (int j = 0; j < U.size()-1; j++){
                H_l.add(U_a[j]);
            }
            for (int j = L.size()-1; j > 0; j--){
                H_l.add(L_a[j]);
            }


            h = H_l.size();
            H_a = mergeArrays(U_a, L_a);
        }
    }

    public int binarySearch(Point2d[]a,Point2d P, int start, int end){
        if(start > end){
            return start;
        }
        int mid = (start + end)/2;
        if(compare(a[mid], P) < 0){
            return binarySearch(a, P, mid+1, end);
        }else{
            return binarySearch(a, P,start, mid - 1);
        }
    }

    public double compare(Point2d P, Point2d Q){
        if(P.x != Q.x){
            return P.x - Q.x;
        }
        else{
            return P.y - Q.y;
        }
    }


    public static Point2d[] mergeArrays(Point2d[] arr1, Point2d[] arr2){
        int l1 = arr1.length;
        int l2 = arr2.length;
        Point2d[] arr3 = new Point2d[l1 + l2 - 2];

        int i = 1;
        int j = 1;
        int k = 1;
        while (i < l1-1 && j < l2-1){
            if (arr1[i].x < arr2[j].x){
                arr3[k++] = arr1[i++];
            }else{
                arr3[k++] = arr2[j++];
            }
        }
        while (i < l1-1){
            arr3[k++] = arr1[i++];
        }
        while(j < l2-1){
            arr3[k++] = arr2[j++];
        }
        arr3[0] = arr1[0];
        arr3[arr3.length - 1] = arr1[arr1.length - 1];

        return arr3;
    }



    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the
       polygon).
    */
    public LinkedList<Point2d> getHull(){

        return H_l;
    }

    /* isInsideHull(P)
       Given an point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){
        int u = binarySearch(U_a, P, 0, U_a.length - 1);
        int l = binarySearch(L_a, P, 0, L_a.length - 1);

        if (u == 0) {
            return compare(U_a[0], P) == 0;
        }

        if (u == U_a.length) {
            return compare(U_a[U_a.length - 1], P) == 0;
        }

        boolean below = Point2d.chirality(U_a[u - 1], U_a[u], P) >= 0;
        boolean above = Point2d.chirality(L_a[l - 1], L_a[l], P) <= 0;

        return below && above;

    }
}
