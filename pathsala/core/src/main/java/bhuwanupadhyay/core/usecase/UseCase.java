package bhuwanupadhyay.core.usecase;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;

/**
 * UseCase
 *
 * @param <I> The request parameter type
 * @param <O> The Result output parameter type
 */
public abstract class UseCase<I, O> {

    /**
     * Handle a UseCase request
     *
     * @param request The UseCase request
     * @return The Result with messages
     */
    public Result<O, Message> execute(I request) {
        return this.doExecute(request);
    }

    /**
     * Handle a UseCase request
     *
     * @param request The UseCase request
     * @return The Result with messages
     */
    protected abstract Result<O, Message> doExecute(I request);

}
