package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProductCatalog.db";
    public static final int DATABASE_VERSION = 7;

    // Table Name
    public static final String TABLE_PRODUCTS = "products";

    // Column Names
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_SKU = "sku";
    public static final String COL_BARCODE = "barcode";
    public static final String COL_CATEGORY = "category";
    public static final String COL_BRAND = "brand";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PURCHASE_PRICE = "purchase_price";
    public static final String COL_SELLING_PRICE = "selling_price";
    public static final String COL_DISCOUNT_PERCENT = "discount_percent";
    public static final String COL_STOCK_QUANTITY = "stock_quantity";
    public static final String COL_LOW_STOCK_THRESHOLD = "low_stock_threshold";
    public static final String COL_SUPPLIER = "supplier";
    public static final String COL_STATUS = "status";
    public static final String COL_AVAILABILITY = "availability";
    public static final String COL_IMAGE_URI = "image_uri";
    public static final String COL_DATE_ADDED = "date_added";
    public static final String COL_LAST_UPDATED = "last_updated";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_SKU + " TEXT UNIQUE NOT NULL, " +
                COL_BARCODE + " TEXT, " +
                COL_CATEGORY + " TEXT NOT NULL, " +
                COL_BRAND + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_PURCHASE_PRICE + " REAL NOT NULL, " +
                COL_SELLING_PRICE + " REAL NOT NULL, " +
                COL_DISCOUNT_PERCENT + " REAL DEFAULT 0.0, " +
                COL_STOCK_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                COL_LOW_STOCK_THRESHOLD + " INTEGER NOT NULL DEFAULT 5, " +
                COL_SUPPLIER + " TEXT, " +
                COL_STATUS + " TEXT NOT NULL DEFAULT 'Active', " +
                COL_AVAILABILITY + " TEXT NOT NULL, " +
                COL_IMAGE_URI + " TEXT, " +
                COL_DATE_ADDED + " TEXT NOT NULL, " +
                COL_LAST_UPDATED + " TEXT NOT NULL)";
        db.execSQL(createTableQuery);

        // Seed with initial realistic inventory products
        seedInitialProducts(db);
    }

    private void seedInitialProducts(SQLiteDatabase db) {
        String today = Product.getCurrentDateString();

        insertInitialProduct(db, "Sony WH-1000XM5 Wireless Headphones", "SKU-SNY-1001",
                "027242923454", "Electronics", "Sony",
                "Industry-leading noise canceling with Auto NC Optimizer and 30-hour battery life.",
                220.00, 399.99, 10.0, 24, 5, "Sony Electronics Corp", "Active", "In Stock", "", today, today);

        insertInitialProduct(db, "Apple MacBook Pro 14 M3", "SKU-APL-8820",
                "195949012345", "Computers & Tech", "Apple",
                "Blazing-fast M3 chip with 8-core CPU, 10-core GPU, 512GB SSD storage.",
                1150.00, 1599.00, 5.0, 8, 4, "Apple Distribution LLC", "Active", "In Stock", "", today, today);

        insertInitialProduct(db, "Nike Air Zoom Pegasus 40", "SKU-NKE-4432",
                "886549281726", "Fashion & Apparel", "Nike",
                "Responsive cushioning and lightweight engineered mesh for daily running.",
                68.00, 130.00, 15.0, 3, 5, "Nike Logistics Intl", "Active", "Low Stock", "", today, today);

        insertInitialProduct(db, "Logitech MX Master 3S Mouse", "SKU-LOG-3310",
                "097855174567", "Computers & Tech", "Logitech",
                "Quiet 8K DPI sensor wireless performance mouse with hyper-fast scrolling.",
                55.00, 99.99, 0.0, 0, 5, "Logitech Global Supply", "Active", "Out of Stock", "", today, today);
    }

    private void insertInitialProduct(SQLiteDatabase db, String name, String sku, String barcode,
                                      String category, String brand, String description,
                                      double purchasePrice, double sellingPrice, double discount,
                                      int stock, int threshold, String supplier, String status,
                                      String availability, String imageUri, String dateAdded, String lastUpdated) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_SKU, sku);
        cv.put(COL_BARCODE, barcode);
        cv.put(COL_CATEGORY, category);
        cv.put(COL_BRAND, brand);
        cv.put(COL_DESCRIPTION, description);
        cv.put(COL_PURCHASE_PRICE, purchasePrice);
        cv.put(COL_SELLING_PRICE, sellingPrice);
        cv.put(COL_DISCOUNT_PERCENT, discount);
        cv.put(COL_STOCK_QUANTITY, stock);
        cv.put(COL_LOW_STOCK_THRESHOLD, threshold);
        cv.put(COL_SUPPLIER, supplier);
        cv.put(COL_STATUS, status);
        cv.put(COL_AVAILABILITY, availability);
        cv.put(COL_IMAGE_URI, imageUri);
        cv.put(COL_DATE_ADDED, dateAdded);
        cv.put(COL_LAST_UPDATED, lastUpdated);
        try {
            db.insert(TABLE_PRODUCTS, null, cv);
        } catch (Exception ignored) {}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    /**
     * Check if a SKU code is already in use by another product
     */
    public boolean isSkuExists(String sku, int excludeId) {
        if (sku == null || sku.trim().isEmpty()) return false;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_ID + " FROM " + TABLE_PRODUCTS +
                " WHERE LOWER(" + COL_SKU + ") = LOWER(?) AND " + COL_ID + " != ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{sku.trim(), "" + excludeId})) {
            return cursor.moveToFirst();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error checking SKU existence", e);
            return false;
        }
    }

    /**
     * Insert a new Product into SQLite
     */
    public long insertProduct(Product product) {
        if (product == null) return -1;

        // Auto compute availability based on stock & threshold
        String availability = Product.computeAvailability(product.getStockQuantity(), product.getLowStockThreshold());
        product.setAvailability(availability);

        String now = Product.getCurrentDateString();
        product.setDateAdded(now);
        product.setLastUpdated(now);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = buildContentValues(product);

        try {
            return db.insert(TABLE_PRODUCTS, null, cv);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting product", e);
            return -1;
        }
    }

    /**
     * Update an existing product
     */
    public boolean updateProduct(Product product) {
        if (product == null || product.getId() <= 0) return false;

        String availability = Product.computeAvailability(product.getStockQuantity(), product.getLowStockThreshold());
        product.setAvailability(availability);
        product.setLastUpdated(Product.getCurrentDateString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = buildContentValues(product);

        try {
            int rows = db.update(TABLE_PRODUCTS, cv, COL_ID + "=?", new String[]{"" + product.getId()});
            return rows > 0;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error updating product", e);
            return false;
        }
    }

    /**
     * Delete a product by its ID
     */
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_PRODUCTS, COL_ID + "=?", new String[]{"" + id});
            return rows > 0;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting product", e);
            return false;
        }
    }

    /**
     * Get a single product by ID
     */
    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COL_ID + " = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{"" + id})) {
            if (cursor.moveToFirst()) {
                return parseProductCursor(cursor);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting product by id", e);
        }
        return null;
    }

    /**
     * Flexible multi-criteria search and filtering for dashboard
     */
    public List<Product> getAllProducts(String searchQuery, String categoryFilter,
                                        String stockStatusFilter, String sortBy) {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("SELECT * FROM ").append(TABLE_PRODUCTS).append(" WHERE 1=1");
        List<String> args = new ArrayList<>();

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            String wildcard = "%" + searchQuery.trim().toLowerCase(Locale.getDefault()) + "%";
            query.append(" AND (LOWER(").append(COL_NAME).append(") LIKE ?")
                 .append(" OR LOWER(").append(COL_SKU).append(") LIKE ?")
                 .append(" OR LOWER(").append(COL_BRAND).append(") LIKE ?")
                 .append(" OR LOWER(").append(COL_SUPPLIER).append(") LIKE ?")
                 .append(" OR LOWER(").append(COL_BARCODE).append(") LIKE ?)");
            args.add(wildcard);
            args.add(wildcard);
            args.add(wildcard);
            args.add(wildcard);
            args.add(wildcard);
        }

        if (categoryFilter != null && !categoryFilter.trim().isEmpty() && !categoryFilter.equalsIgnoreCase("All Categories") && !categoryFilter.equalsIgnoreCase("All")) {
            query.append(" AND ").append(COL_CATEGORY).append(" = ?");
            args.add(categoryFilter.trim());
        }

        if (stockStatusFilter != null && !stockStatusFilter.trim().isEmpty() && !stockStatusFilter.equalsIgnoreCase("All Stock") && !stockStatusFilter.equalsIgnoreCase("All")) {
            query.append(" AND ").append(COL_AVAILABILITY).append(" = ?");
            args.add(stockStatusFilter.trim());
        }

        // Sorting
        if ("Price: Low to High".equalsIgnoreCase(sortBy)) {
            query.append(" ORDER BY ").append(COL_SELLING_PRICE).append(" ASC");
        } else if ("Price: High to Low".equalsIgnoreCase(sortBy)) {
            query.append(" ORDER BY ").append(COL_SELLING_PRICE).append(" DESC");
        } else if ("Stock: Low to High".equalsIgnoreCase(sortBy)) {
            query.append(" ORDER BY ").append(COL_STOCK_QUANTITY).append(" ASC");
        } else if ("Name A-Z".equalsIgnoreCase(sortBy)) {
            query.append(" ORDER BY ").append(COL_NAME).append(" ASC");
        } else {
            // Default: Newest first
            query.append(" ORDER BY ").append(COL_ID).append(" DESC");
        }

        try (Cursor cursor = db.rawQuery(query.toString(), args.toArray(new String[0]))) {
            while (cursor.moveToNext()) {
                list.add(parseProductCursor(cursor));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting all products", e);
        }

        return list;
    }

    /**
     * Compute real-time dashboard KPIs
     */
    public DashboardStats getDashboardStats() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        int active = 0;
        int lowStock = 0;
        int outOfStock = 0;
        double inventoryValue = 0.0;
        double retailValue = 0.0;

        String query = "SELECT " + COL_STATUS + ", " + COL_AVAILABILITY + ", " +
                COL_PURCHASE_PRICE + ", " + COL_SELLING_PRICE + ", " +
                COL_STOCK_QUANTITY + ", " + COL_LOW_STOCK_THRESHOLD + " FROM " + TABLE_PRODUCTS;

        try (Cursor cursor = db.rawQuery(query, null)) {
            total = cursor.getCount();
            while (cursor.moveToNext()) {
                String status = cursor.getString(0);
                String avail = cursor.getString(1);
                double purchase = cursor.getDouble(2);
                double selling = cursor.getDouble(3);
                int stock = cursor.getInt(4);
                int threshold = cursor.getInt(5);

                if ("Active".equalsIgnoreCase(status)) {
                    active++;
                }

                if (stock <= 0 || "Out of Stock".equalsIgnoreCase(avail)) {
                    outOfStock++;
                } else if (stock <= threshold || "Low Stock".equalsIgnoreCase(avail)) {
                    lowStock++;
                }

                inventoryValue += (purchase * stock);
                retailValue += (selling * stock);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error computing dashboard stats", e);
        }

        return new DashboardStats(total, active, lowStock, outOfStock, inventoryValue, retailValue);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " ORDER BY " + COL_ID + " DESC", null);
    }

    public int getRecordsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS, null)) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting records count", e);
        }
        return count;
    }

    private ContentValues buildContentValues(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, product.getName());
        cv.put(COL_SKU, product.getSku());
        cv.put(COL_BARCODE, product.getBarcode());
        cv.put(COL_CATEGORY, product.getCategory());
        cv.put(COL_BRAND, product.getBrand());
        cv.put(COL_DESCRIPTION, product.getDescription());
        cv.put(COL_PURCHASE_PRICE, product.getPurchasePrice());
        cv.put(COL_SELLING_PRICE, product.getSellingPrice());
        cv.put(COL_DISCOUNT_PERCENT, product.getDiscountPercent());
        cv.put(COL_STOCK_QUANTITY, product.getStockQuantity());
        cv.put(COL_LOW_STOCK_THRESHOLD, product.getLowStockThreshold());
        cv.put(COL_SUPPLIER, product.getSupplier());
        cv.put(COL_STATUS, product.getStatus());
        cv.put(COL_AVAILABILITY, product.getAvailability());
        cv.put(COL_IMAGE_URI, product.getImageUri());
        cv.put(COL_DATE_ADDED, product.getDateAdded());
        cv.put(COL_LAST_UPDATED, product.getLastUpdated());
        return cv;
    }

    private Product parseProductCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndexOrThrow(COL_ID));
        String name = c.getString(c.getColumnIndexOrThrow(COL_NAME));
        String sku = c.getString(c.getColumnIndexOrThrow(COL_SKU));
        String barcode = c.getString(c.getColumnIndexOrThrow(COL_BARCODE));
        String category = c.getString(c.getColumnIndexOrThrow(COL_CATEGORY));
        String brand = c.getString(c.getColumnIndexOrThrow(COL_BRAND));
        String desc = c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTION));
        double purchase = c.getDouble(c.getColumnIndexOrThrow(COL_PURCHASE_PRICE));
        double selling = c.getDouble(c.getColumnIndexOrThrow(COL_SELLING_PRICE));
        double discount = c.getDouble(c.getColumnIndexOrThrow(COL_DISCOUNT_PERCENT));
        int stock = c.getInt(c.getColumnIndexOrThrow(COL_STOCK_QUANTITY));
        int threshold = c.getInt(c.getColumnIndexOrThrow(COL_LOW_STOCK_THRESHOLD));
        String supplier = c.getString(c.getColumnIndexOrThrow(COL_SUPPLIER));
        String status = c.getString(c.getColumnIndexOrThrow(COL_STATUS));
        String avail = c.getString(c.getColumnIndexOrThrow(COL_AVAILABILITY));
        String img = c.getString(c.getColumnIndexOrThrow(COL_IMAGE_URI));
        String dateAdded = c.getString(c.getColumnIndexOrThrow(COL_DATE_ADDED));
        String lastUpdated = c.getString(c.getColumnIndexOrThrow(COL_LAST_UPDATED));

        return new Product(id, name, sku, barcode, category, brand, desc, purchase, selling,
                discount, stock, threshold, supplier, status, avail, img, dateAdded, lastUpdated);
    }
}
