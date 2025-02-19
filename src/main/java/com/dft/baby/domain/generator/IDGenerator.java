package com.dft.baby.domain.generator;

import com.dft.baby.web.exception.CommonException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

import static com.dft.baby.web.exception.ExceptionType.UNKNOWN_ERROR;
import static com.dft.baby.web.util.Util.EPOCH;

public class IDGenerator implements IdentifierGenerator {

    private final long epoch = EPOCH;
    private final long serverId = 1L;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private final Object lock = new Object();

    private final long serverIdBits = 5L;
    private final long sequenceBits = 12L;
    private final long maxServerId = ~(-1L << serverIdBits);
    private final long maxSequence = ~(-1L << sequenceBits);

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis();
        }
        return timestamp;
    }

    public long generateId() {
        synchronized (lock) {
            long timestamp = currentTimeMillis();

            if (timestamp < lastTimestamp) {
                throw new CommonException(UNKNOWN_ERROR.getCode(), UNKNOWN_ERROR.getErrorMessage());
            }

            if (timestamp == lastTimestamp) {
                sequence = (sequence + 1) & maxSequence;
                if (sequence == 0) {
                    timestamp = waitForNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0;
            }

            lastTimestamp = timestamp;
            return ((timestamp - epoch) << (serverIdBits + sequenceBits)) | (serverId << sequenceBits) | sequence;
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return generateId();
    }

    public long extractSequence(long id) {
        return id & maxSequence;
    }

    public long extractServerId(long id) {
        return (id >> sequenceBits) & maxServerId;
    }

    public long extractTimestamp(long id) {
        return (id >> (serverIdBits + sequenceBits)) + epoch;
    }
}