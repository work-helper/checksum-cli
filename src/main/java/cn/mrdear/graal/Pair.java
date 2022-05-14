package cn.mrdear.graal;

/**
 * @author quding
 * @since 2022/5/14
 */
public class Pair<L, R> {

    public L left;

    public R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

}
