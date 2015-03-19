/**
 * Email:tu226@foxmail.com
 */

import com.google.common.collect.ComparisonChain;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public final class DynamicComparator implements Comparator<Object>, Serializable {

    private static final String LOG_MSG_UNSUPPORTED_TYPE = "DynamicComparator does not support \"{0}\"";
    private static final String LOG_MSG_ERROR = "Error: {0}";
    private static final long serialVersionUID = 2177333825848980856L;
    private String property = null;
    private boolean sortByAsc = true;

    public DynamicComparator(String property, boolean sortByAsc) {
        super();
        this.property = property;
        this.sortByAsc = sortByAsc;
    }

    public static void sort(List<?> list, String field, boolean sortAsc) {
        Collections.sort(list, new DynamicComparator(field, sortAsc));
    }

    public int compare(Object o1, Object o2) {
        try {
            String returnType = PropertyUtils.getPropertyType(o1, this.property).getName();
            Object p1 = PropertyUtils.getProperty(o1, this.property);
            Object p2 = PropertyUtils.getProperty(o2, this.property);

            if (returnType.equals("java.util.Date"))
                return ComparisonChain.start().compare((Date) p1, (Date) p2).result() * getSortOrder();

            if (returnType.equals("java.lang.String"))
                return ComparisonChain.start().compare((String) p1, (String) p2).result() * getSortOrder();

            if (returnType.equals("char") || returnType.equals("java.lang.Character"))
                return ComparisonChain.start().compare((Character) p1, (Character) p2).result() * getSortOrder();

            if (returnType.equals("boolean") || returnType.equals("java.lang.Boolean"))
                return ComparisonChain.start().compare((Boolean) p1, (Boolean) p2).result() * getSortOrder();

            if (returnType.equals("byte") || returnType.equals("java.lang.Byte"))
                return ComparisonChain.start().compare((Byte) p1, (Byte) p2).result() * getSortOrder();

            if (returnType.equals("short") || returnType.equals("java.lang.Short"))
                return ComparisonChain.start().compare((Short) p1, (Short) p2).result() * getSortOrder();

            if (returnType.equals("int") || returnType.equals("java.lang.Integer"))
                return ComparisonChain.start().compare((Integer) p1, (Integer) p2).result() * getSortOrder();

            if (returnType.equals("long") || returnType.equals("java.lang.Long"))
                return ComparisonChain.start().compare((Long) p1, (Long) p2).result() * getSortOrder();

            if (returnType.equals("double") || returnType.equals("java.lang.Double"))
                return ComparisonChain.start().compare((Double) p1, (Double) p2).result() * getSortOrder();

            if (returnType.equals("float") || returnType.equals("java.lang.Float"))
                return ComparisonChain.start().compare((Float) p1, (Float) p2).result() * getSortOrder();

            throw new RuntimeException(MessageFormat.format(
                    LOG_MSG_UNSUPPORTED_TYPE, new Object[]{returnType}));

        } catch (NoSuchMethodException nsme) {
            System.out.println(MessageFormat.format(LOG_MSG_ERROR,
                    new Object[]{nsme.getMessage()}));
            System.out.println(nsme);
        } catch (IllegalAccessException iae) {
            System.out.println(MessageFormat.format(LOG_MSG_ERROR,
                    new Object[]{iae.getMessage()}));
            System.out.println(iae);
        } catch (InvocationTargetException ite) {
            System.out.println(MessageFormat.format(LOG_MSG_ERROR,
                    new Object[]{ite.getMessage()}));
            System.out.println(ite);
        }

        return 0;
    }

    private int getSortOrder() {
        return sortByAsc ? 1 : -1;
    }
}
