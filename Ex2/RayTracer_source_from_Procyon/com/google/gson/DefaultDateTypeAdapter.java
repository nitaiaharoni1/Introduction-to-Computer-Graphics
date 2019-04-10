// 
// Decompiled by Procyon v0.5.30
// 

package com.google.gson;

import java.text.ParseException;
import com.google.gson.internal.bind.util.ISO8601Utils;
import java.text.ParsePosition;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import com.google.gson.stream.JsonWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;

final class DefaultDateTypeAdapter extends TypeAdapter<Date>
{
    private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
    private final Class<? extends Date> dateType;
    private final DateFormat enUsFormat;
    private final DateFormat localFormat;
    
    DefaultDateTypeAdapter(final Class<? extends Date> dateType) {
        this(dateType, DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }
    
    DefaultDateTypeAdapter(final Class<? extends Date> dateType, final String datePattern) {
        this(dateType, new SimpleDateFormat(datePattern, Locale.US), new SimpleDateFormat(datePattern));
    }
    
    DefaultDateTypeAdapter(final Class<? extends Date> dateType, final int style) {
        this(dateType, DateFormat.getDateInstance(style, Locale.US), DateFormat.getDateInstance(style));
    }
    
    public DefaultDateTypeAdapter(final int dateStyle, final int timeStyle) {
        this(Date.class, DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US), DateFormat.getDateTimeInstance(dateStyle, timeStyle));
    }
    
    public DefaultDateTypeAdapter(final Class<? extends Date> dateType, final int dateStyle, final int timeStyle) {
        this(dateType, DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US), DateFormat.getDateTimeInstance(dateStyle, timeStyle));
    }
    
    DefaultDateTypeAdapter(final Class<? extends Date> dateType, final DateFormat enUsFormat, final DateFormat localFormat) {
        if (dateType != Date.class && dateType != java.sql.Date.class && dateType != Timestamp.class) {
            throw new IllegalArgumentException("Date type must be one of " + Date.class + ", " + Timestamp.class + ", or " + java.sql.Date.class + " but was " + dateType);
        }
        this.dateType = dateType;
        this.enUsFormat = enUsFormat;
        this.localFormat = localFormat;
    }
    
    @Override
    public void write(final JsonWriter out, final Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        synchronized (this.localFormat) {
            final String dateFormatAsString = this.enUsFormat.format(value);
            out.value(dateFormatAsString);
        }
    }
    
    @Override
    public Date read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        final Date date = this.deserializeToDate(in.nextString());
        if (this.dateType == Date.class) {
            return date;
        }
        if (this.dateType == Timestamp.class) {
            return new Timestamp(date.getTime());
        }
        if (this.dateType == java.sql.Date.class) {
            return new java.sql.Date(date.getTime());
        }
        throw new AssertionError();
    }
    
    private Date deserializeToDate(final String s) {
        synchronized (this.localFormat) {
            try {
                return this.localFormat.parse(s);
            }
            catch (ParseException ex) {
                try {
                    return this.enUsFormat.parse(s);
                }
                catch (ParseException ex2) {
                    try {
                        // monitorexit(this.localFormat)
                        return ISO8601Utils.parse(s, new ParsePosition(0));
                    }
                    catch (ParseException e) {
                        throw new JsonSyntaxException(s, e);
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DefaultDateTypeAdapter");
        sb.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
        return sb.toString();
    }
}
