package openfoodfacts.github.scrachx.openfood.utils;

import openfoodfacts.github.scrachx.openfood.models.OfflineSavedProduct;
import openfoodfacts.github.scrachx.openfood.models.OfflineSavedProductDao;
import openfoodfacts.github.scrachx.openfood.views.OFFApplication;

public class OfflineProductService {
    private static OfflineSavedProductDao getOfflineProductDAO() {
        return OFFApplication.getInstance().getDaoSession().getOfflineSavedProductDao();
    }

    public static OfflineSavedProduct getOfflineProductByBarcode(String barcode) {
        return getOfflineProductDAO().queryBuilder().where(OfflineSavedProductDao.Properties.Barcode.eq(barcode)).unique();
    }
}
