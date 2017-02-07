package info.blockchain.wallet;

import retrofit2.Retrofit;

/**
 * Class for initializing an instance of the Wallet JAR
 */
public final class BlockchainFramework {

    private static FrameworkInterface blockchainInterface;

    public static void init(FrameworkInterface frameworkInterface) {
        blockchainInterface = frameworkInterface;
    }

    public static Retrofit getRetrofitApiInstance() {
        return blockchainInterface.getRetrofitApiInstance();
    }

    public static Retrofit getRetrofitServerInstance() {
        return blockchainInterface.getRetrofitServerInstance();
    }

    public static String getApiCode() {
        return blockchainInterface.getApiCode();
    }
}
