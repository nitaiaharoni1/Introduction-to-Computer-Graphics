// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util.locks;

import com.jogamp.common.util.locks.Lock;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LockDebugUtil
{
    private static final ThreadLocal<ArrayList<Throwable>> tlsLockedStacks;
    private static final List<Throwable> dummy;
    
    public static List<Throwable> getRecursiveLockTrace() {
        if (Lock.DEBUG) {
            ArrayList<Throwable> list = LockDebugUtil.tlsLockedStacks.get();
            if (null == list) {
                list = new ArrayList<Throwable>();
                LockDebugUtil.tlsLockedStacks.set(list);
            }
            return list;
        }
        return LockDebugUtil.dummy;
    }
    
    public static void dumpRecursiveLockTrace(final PrintStream printStream) {
        if (Lock.DEBUG) {
            final List<Throwable> recursiveLockTrace = getRecursiveLockTrace();
            if (null != recursiveLockTrace && recursiveLockTrace.size() > 0) {
                int n = 0;
                printStream.println("TLSLockedStacks: locks " + recursiveLockTrace.size());
                final Iterator<Throwable> iterator = recursiveLockTrace.iterator();
                while (iterator.hasNext()) {
                    printStream.print(n + ": ");
                    iterator.next().printStackTrace(printStream);
                    ++n;
                }
            }
        }
    }
    
    static {
        if (Lock.DEBUG) {
            tlsLockedStacks = new ThreadLocal<ArrayList<Throwable>>();
            dummy = null;
        }
        else {
            tlsLockedStacks = null;
            dummy = new ArrayList<Throwable>(0);
        }
    }
}
