package info.blockchain.wallet.payment;

import info.blockchain.api.data.UnspentOutput;
import info.blockchain.api.data.UnspentOutputs;
import info.blockchain.wallet.BlockchainFramework;
import info.blockchain.wallet.api.Wallet2;
import info.blockchain.wallet.api.data.FeesItem;
import info.blockchain.wallet.api.data.FeesResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nonnull;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.tuple.Pair;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import retrofit2.Call;

public class Payment {

    public static final BigInteger PUSHTX_MIN = BigInteger
        .valueOf(Coin.parseCoin("0.00001").longValue());
    public static final BigInteger DUST = BigInteger
        .valueOf(Coin.parseCoin("0.000005460").longValue());

    /**********************************************************************************************/
    /*                                     Fee Handling                                           */
    /**********************************************************************************************/
    public static BigInteger estimatedFee(int inputs, int outputs, @Nonnull BigInteger feePerKb) {
        return Fees.estimatedFee(inputs, outputs, feePerKb);
    }

    public static int estimatedSize(int inputs, int outputs) {
        return Fees.estimatedSize(inputs, outputs);
    }

    public static boolean isAdequateFee(int inputs, int outputs, @Nonnull BigInteger absoluteFee) {
        return Fees.isAdequateFee(inputs, outputs, absoluteFee);
    }

    public static Call<FeesResponse> getDynamicFee() {
        return Wallet2.getDynamicFee();
    }

    public static FeesItem getDefaultFee() throws IOException {
        return Wallet2.getDefaultFee();
    }

    /**********************************************************************************************/
    /*                                     Coin selection                                         */
    /**********************************************************************************************/
    public static Call<UnspentOutputs> getUnspentCoins(@Nonnull List<String> addresses)
        throws IOException {
        return Coins.getUnspentCoins(addresses);
    }

    public static Pair<BigInteger, BigInteger> getSweepableCoins(@Nonnull UnspentOutputs unspentCoins,
        @Nonnull BigInteger feePerKb) {
        return Coins.getSweepableCoins(unspentCoins, feePerKb);
    }

    public static PaymentBundle getCoinsForPayment(@Nonnull UnspentOutputs unspentCoins,
        @Nonnull BigInteger paymentAmount, @Nonnull BigInteger feePerKb)
        throws UnsupportedEncodingException {
        return Coins.getMinimumCoinsForPayment(unspentCoins, paymentAmount, feePerKb);
    }

    /**********************************************************************************************/
    /*                                     Transaction                                            */
    /**********************************************************************************************/
    public static Transaction makeTransaction(@Nonnull List<UnspentOutput> unspentCoins,
        @Nonnull HashMap<String, BigInteger> receivingAddresses,
        @Nonnull BigInteger fee,
        @Nonnull String changeAddress)
        throws InsufficientMoneyException, AddressFormatException {
        return PaymentTx.makeTransaction(unspentCoins, receivingAddresses, fee, changeAddress);
    }

    public static void signTransaction(@Nonnull Transaction transaction, @Nonnull List<ECKey> keys) {
        PaymentTx.signTransaction(transaction, keys);
    }

    public static Call<ResponseBody> publishTransaction(@Nonnull Transaction transaction)
        throws IOException {
        return PaymentTx.publishTransaction(transaction, BlockchainFramework.getApiCode());
    }
}
