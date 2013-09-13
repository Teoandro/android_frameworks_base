/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.print;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * This class encapsulates information about a printed document.
 */
public final class PrintDocumentInfo implements Parcelable {

    /**
     * Constant for unknown page count..
     */
    public static final int PAGE_COUNT_UNKNOWN = -1;

    /**
     * Content type: unknown.
     */
    public static final int CONTENT_TYPE_UNKNOWN = -1;

    /**
     * Content type: document.
     */
    public static final int CONTENT_TYPE_DOCUMENT = 0;

    /**
     * Content type: photo.
     */
    public static final int CONTENT_TYPE_PHOTO = 1;

    private String mName;
    private int mPageCount;
    private int mContentType;
    private long mDataSize;

    /**
     * Creates a new instance.
     */
    private PrintDocumentInfo() {
        /* do nothing */
    }

    /**
     * Creates a new instance.
     *
     * @param Prototype from which to clone.
     */
    private PrintDocumentInfo(PrintDocumentInfo prototype) {
        mName = prototype.mName;
        mPageCount = prototype.mPageCount;
        mContentType = prototype.mContentType;
        mDataSize = prototype.mDataSize;
    }

    /**
     * Creates a new instance.
     *
     * @param parcel Data from which to initialize.
     */
    private PrintDocumentInfo(Parcel parcel) {
        mName = parcel.readString();
        mPageCount = parcel.readInt();
        mContentType = parcel.readInt();
        mDataSize = parcel.readLong();
    }

    /**
     * Gets the document name.
     *
     * @return The document name.
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets the total number of pages.
     *
     * @return The number of pages.
     *
     * @see #PAGE_COUNT_UNKNOWN
     */
    public int getPageCount() {
        return mPageCount;
    }

    /**
     * Gets the content type.
     *
     * @return The content type.
     *
     * @see #CONTENT_TYPE_UNKNOWN
     * @see #CONTENT_TYPE_DOCUMENT
     * @see #CONTENT_TYPE_PHOTO
     */
    public int getContentType() {
        return mContentType;
    }

    /**
     * Gets the document data size in bytes.
     *
     * @return The data size.
     */
    public long getDataSize() {
        return mDataSize;
    }

    /**
     * Sets the document data size in bytes.
     *
     * @param dataSize The data size.
     *
     * @hide
     */
    public void setDataSize(long dataSize) {
        mDataSize = dataSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mName);
        parcel.writeInt(mPageCount);
        parcel.writeInt(mContentType);
        parcel.writeLong(mDataSize);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mName != null) ? mName.hashCode() : 0);
        result = prime * result + mContentType;
        result = prime * result + mPageCount;
        result = prime * result + (int) mDataSize;
        result = prime * result + (int) mDataSize >> 32;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PrintDocumentInfo other = (PrintDocumentInfo) obj;
        if (!TextUtils.equals(mName, other.mName)) {
            return false;
        }
        if (mContentType != other.mContentType) {
            return false;
        }
        if (mPageCount != other.mPageCount) {
            return false;
        }
        if (mDataSize != other.mDataSize) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PrintDocumentInfo{");
        builder.append("name=").append(mName);
        builder.append(", pageCount=").append(mPageCount);
        builder.append(", contentType=").append(contentTyepToString(mContentType));
        builder.append(", size=").append(mDataSize);
        builder.append("}");
        return builder.toString();
    }

    private String contentTyepToString(int contentType) {
        switch (contentType) {
            case CONTENT_TYPE_DOCUMENT: {
                return "CONTENT_TYPE_DOCUMENT";
            }
            case CONTENT_TYPE_PHOTO: {
                return "CONTENT_TYPE_PHOTO";
            }
            default: {
                return "CONTENT_TYPE_UNKNOWN";
            }
        }
    }

    /**
     * Builder for creating an {@link PrintDocumentInfo}.
     */
    public static final class Builder {
        private final PrintDocumentInfo mPrototype;

        /**
         * Constructor.
         * <p>
         * The values of the relevant properties are initialized with default
         * values. Please refer to the documentation of the individual setters
         * for information about the default values.
         * </p>
         *
         * @param name The document name. Cannot be empty. 
         */
        public Builder(String name) {
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("name cannot be empty");
            }
            mPrototype = new PrintDocumentInfo();
            mPrototype.mName = name;
        }

        /**
         * Sets the total number of pages.
         * <p>
         * <strong>Default: </strong> {@link #PAGE_COUNT_UNKNOWN}
         * </p>
         *
         * @param pageCount The number of pages. Must be greater than
         * or equal to zero or {@link PrintDocumentInfo#PAGE_COUNT_UNKNOWN}.
         */
        public Builder setPageCount(int pageCount) {
            if (pageCount < 0 && pageCount != PAGE_COUNT_UNKNOWN) {
                throw new IllegalArgumentException("pageCount"
                        + " must be greater than or euqal to zero or"
                        + " DocumentInfo#PAGE_COUNT_UNKNOWN");
            }
            mPrototype.mPageCount = pageCount;
            return this;
        }

        /**
         * Sets the content type.
         * <p>
         * <strong>Default: </strong> {@link #CONTENT_TYPE_UNKNOWN}
         * </p>
         *
         * @param type The content type.
         *
         * @see #CONTENT_TYPE_UNKNOWN
         * @see #CONTENT_TYPE_DOCUMENT
         * @see #CONTENT_TYPE_PHOTO
         */
        public Builder setContentType(int type) {
            mPrototype.mContentType = type;
            return this;
        }

        /**
         * Creates a new {@link PrintDocumentInfo} instance.
         *
         * @return The new instance.
         */
        public PrintDocumentInfo build() {
            return new PrintDocumentInfo(mPrototype);
        }
    }

    public static final Parcelable.Creator<PrintDocumentInfo> CREATOR =
            new Creator<PrintDocumentInfo>() {
        @Override
        public PrintDocumentInfo createFromParcel(Parcel parcel) {
            return new PrintDocumentInfo(parcel);
        }

        @Override
        public PrintDocumentInfo[] newArray(int size) {
            return new PrintDocumentInfo[size];
        }
    };
}
