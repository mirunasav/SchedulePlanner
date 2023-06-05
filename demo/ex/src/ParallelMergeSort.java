import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelMergeSort {
    private static final int THRESHOLD = 10; // Threshold for switching to normal sort

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(9, 3, 7, 1, 5, 6, 4, 2, 8);

        System.out.println("Original List: " + numbers);

        List<Integer> sortedNumbers = parallelMergeSort(numbers);

        System.out.println("Sorted List: " + sortedNumbers);
    }

    public static List<Integer> parallelMergeSort(List<Integer> numbers) {
        if (numbers.size() <= THRESHOLD) {
            // If the list is shorter than or equal to the threshold, use normal sort
            numbers.sort(null);
            return numbers;
        } else {
            // Split the list into two sublists
            int mid = numbers.size() / 2;
            List<Integer> leftSublist = numbers.subList(0, mid);
            List<Integer> rightSublist = numbers.subList(mid, numbers.size());

            // Create two threads for sorting each sublist
            Thread leftThread = new Thread(() -> parallelMergeSort(leftSublist));
            Thread rightThread = new Thread(() -> parallelMergeSort(rightSublist));

            // Start the threads
            leftThread.start();
            rightThread.start();

            // Wait for the threads to finish
            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Merge the sorted sublists
            List<Integer> mergedList = merge(leftSublist, rightSublist);

            return mergedList;
        }
    }

    private static List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> merged = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) < right.get(rightIndex)) {
                merged.add(left.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        // Add remaining elements from left sublist
        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex));
            leftIndex++;
        }

        // Add remaining elements from right sublist
        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex));
            rightIndex++;
        }

        return merged;
    }
}