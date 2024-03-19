package io.github.kennethfan.leetcode;

/**
 * Created by kenneth on 2023/5/30.
 */
public class MidNum {

    public static double mid(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            throw new IllegalArgumentException();
        }

        final int len1 = nums1.length;
        final int len2 = nums2.length;

        if (len1 == 0 && len2 == 0) {
            throw new IllegalArgumentException();
        }

        int total = len1 + len2;
        int mid = total / 2;
        if (total % 2 == 1) {
            return getKthElement(nums1, nums2, mid + 1);
        }

        return (getKthElement(nums1, nums2, mid) + getKthElement(nums1, nums2, mid + 1)) / 2.0D;
    }

    /**
     * 求两个有序数组中第k个元素
     * <p>
     * 思路：
     * 1、比较数组1中第k / 2 - 1个元素nk1和数组2中第k / 2 - 1个元素nk2
     * 2、nk1 <= nk2，则可以忽略数组1中k / 2 - 1个元素
     * 3、nk1 >= nk2，则可以忽略数组2中k / 2 - 1个元素
     * 4、当次忽略元素为m
     * 5、问题转化为求剩余有序数组中第k-m个元素
     * 边界值处理
     * 1、如果数组已经到越界了，则直接从另一个数组中取剩下的元素
     * 2、k=1时直接取两个数组中当前索引最小的元素
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    private static int getKthElement(int[] nums1, int[] nums2, int k) {
        final int len1 = nums1.length;
        final int len2 = nums2.length;

        int left1 = 0;
        int left2 = 0;

        while (true) {
            // 数组1已经全部被排除
            if (left1 == len1) {
                return nums2[left2 + k - 1];
            }

            // 数组2已经全部被排除
            if (left2 == len2) {
                return nums1[left1 + k - 1];
            }

            if (k == 1) {
                return Math.min(nums1[left1], nums2[left2]);
            }

            int half = k / 2;
            int midIndex1 = Math.min(left1 + half, len1) - 1;
            int midIndex2 = Math.min(left2 + half, len2) - 1;
            if (nums1[midIndex1] < nums2[midIndex2]) {
                k -= (midIndex1 - left1 + 1);
                left1 = midIndex1 + 1;
            } else {
                k -= (midIndex2 - left2 + 1);
                left2 = midIndex2 + 1;
            }
        }
    }

    public static double mid2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            throw new IllegalArgumentException();
        }

        final int len1 = nums1.length;
        final int len2 = nums2.length;

        if (len1 == 0 && len2 == 0) {
            throw new IllegalArgumentException();
        }

        final boolean even1 = len1 % 2 == 0;
        final boolean even2 = len2 % 2 == 0;
        final boolean even = (even1 == even2);

        int skipLen;
        if (len1 == 0) {
            skipLen = len2 / 2;
            if (even2) {
                skipLen -= 1;
                return (nums2[skipLen] + nums2[skipLen + 1]) / 2.0D;
            }
            return nums2[skipLen];
        }

        if (len2 == 0) {
            skipLen = len1 / 2;
            if (even1) {
                skipLen -= 1;
                return (nums1[skipLen] + nums1[skipLen + 1]) / 2.0D;
            }
            return nums1[skipLen];
        }

        if (len1 == len2) {
            skipLen = len1 - 1;
        } else {
            skipLen = len1 / 2 + len2 / 2;
            if (even1 && even2) {
                skipLen -= 1;
            }
        }

        int i = 0;
        int j = 0;
        int skip = 0;
        while (i < len1 && j < len2 && skip < skipLen) {
            if (nums1[i] < nums2[j]) {
                i++;

            } else {
                j++;
            }

            skip++;
        }

        if (i == len1) {
            int index = skipLen - len1;
            if (even) {
                return (nums2[index] + nums2[index + 1]) / 2.0D;
            }
            return nums2[index];
        }

        if (j == len2) {
            int index = skipLen - len2;
            if (even) {
                return (nums1[index] + nums1[index + 1]) / 2.0D;
            }
            return nums1[index];
        }

        int value = nums1[i] < nums2[j] ? nums1[i++] : nums2[j++];
        if (!even) {
            return value;
        }

        if (i == len1) {
            return (value + nums2[j]) / 2.0D;
        } else if (j == len2) {
            return (value + nums1[i]) / 2.0D;
        } else {
            value += nums1[i] < nums2[j] ? nums1[i] : nums2[j];
            return value / 2.0D;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 7};
        int[] nums2 = {2, 4, 6};
        System.out.println(mid(nums1, nums2));

        int[] nums3 = {2, 4, 6, 8};
        System.out.println(mid(nums1, nums3));

        int[] nums4 = {2};
        System.out.println(mid(nums1, nums4));

        int[] nums5 = {1, 3, 5, 7, 9};
        System.out.println(mid(nums4, nums5));

        int[] nums6 = {3};
        System.out.println(mid(nums4, nums6));

        int[] nums7 = {};
        System.out.println(mid(nums4, nums7));

        System.out.println(mid(nums1, nums7));
    }
}
