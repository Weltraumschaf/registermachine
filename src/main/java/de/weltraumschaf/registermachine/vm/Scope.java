/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.registermachine.vm;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import de.weltraumschaf.registermachine.ByteArray;
import java.util.List;
import java.util.Map;

public class Scope {

    private final Map<Integer, List<Byte>> assigns = Maps.newHashMap();

    public void setAssign(final int address, final byte[] value) {
        assigns.put(address, Bytes.asList(value));
    }

    /**
     *
     * @param address
     * @return
     * @throws IndexOutOfBoundsException
     */
    public byte[] getAssign(final int address) {
        final Byte[] objects = assigns.get(address).toArray(new Byte[assigns.size()]);
        return ByteArray.toNative(objects);
    }

    public boolean isEmpty() {
        return assigns.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb  = new StringBuilder();
        sb.append("\t\t");
        final int cnt = assigns.size();

        for (int i = 0; i < cnt; i++) {
            if (i != 0) {
                sb.append(",\t");
            }

            sb.append("s[").append(i).append("] = ").append(assigns.get(i));
        }

        return sb.toString();
    }

}