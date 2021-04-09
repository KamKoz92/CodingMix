public class CanSum {

    public static void main(String[] args) {
        int[] vector = new int[]{2,4};

        System.out.println(canSum(7,vector));
    }



    public static boolean canSum(int target, int[] vector) {
        if(target < 0 ) return false;
        if(target == 0) return true;
        for(int i = 0; i < vector.length; i++) {
            if(target - vector[i] == 0) {
                return true;
            } else {
                canSum(target-vector[i], vector);
            }
        }
        return false;
    }
}
