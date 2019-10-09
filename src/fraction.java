/**
 *把所有随机生成的数都当成是分数处理
 * 定义分数的四则运算类
 * 生成随机的分子分母
 */
public class fraction{

    private int mol; //分子
    private int den;  //分母

    /**
     * 处理随机生成的数值,这个用于分解分数对象(的check的时候用)
     */
    fraction(String str) {
        int a = str.indexOf("'");
        int b = str.indexOf("/");
        if (a != -1) {
            //取出数组，转换类型
            int c = Integer.valueOf(str.substring(0, a));
            den = Integer.valueOf(str.substring(b + 1));
            mol = c * den + Integer.valueOf(str.substring(a + 1, b));
        } else if (b != -1) {
            String[] sirs = str.split("/");
            mol = Integer.valueOf(sirs[0]);
            den = Integer.valueOf(sirs[1]);
        } else {
            mol = Integer.valueOf(str);
            den = 1;
        }
    }

    /**
     * 处理随机生成的数值,组合分数对象，也是重载这个fraction方法，后面调用
     */
    fraction(int mol, int den) {
        this.mol = mol;
        this.den = den;
        if (den <= 0) {
            throw new RuntimeException("分数分母不能为0");
        }
        //否则就进行约分
        int mod = 1;
        int max = den > mol ? den : mol;
        for (int i = 1; i <= max; i++) {
            if (mol % i == 0 && den % i == 0) {
                mod = i;
            }
        }
        this.mol = mol / mod;
        this.den = den / mod;
    }

    public String toString() {
        if (den == 1) {
            return String.valueOf(mol);
        } else {
            int z = 0;
            if (den != 0 && mol > den) {
                z = mol / den;
            }
            if (z == 0) {
                return String.valueOf(mol) + "/" + String.valueOf(den);
            } else {
                return String.valueOf(z) + "'" + String.valueOf(mol % den) + "/" + String.valueOf(den);
            }
        }
    }

    /**
     * 判空
     */
    boolean Zero() {
        return den == 0;
    }

    /**
     * 判负
     */
    boolean Negative() {
        return mol < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof fraction)) return false;
        fraction fraction = (fraction) o;
        return mol == fraction.mol &&
                den == fraction.den;
    }
    /**
     * 定义加减乘除类,返回值类型(全都当成分数处理)
     */
    fraction add(fraction fraction) {
        return new fraction(this.mol * fraction.den + this.den * fraction.mol, this.den * fraction.den);
    }

    fraction subtract(fraction fraction) {
        return new fraction(this.mol * fraction.den - this.den * fraction.mol, this.den * fraction.den);
    }

    fraction multiply(fraction fraction) {
        return new fraction(this.mol * fraction.mol, this.den * fraction.den);
    }

    fraction divide(fraction fraction) {
        return new fraction(this.mol * fraction.den, this.den * fraction.mol);
    }
}

