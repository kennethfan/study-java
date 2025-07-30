package io.github.kennethfan.wuying;

// This file is auto-generated, don't edit it. Thanks.

import com.alibaba.fastjson.JSON;
import com.aliyun.eds_aic20230930.models.*;
import com.aliyun.tea.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Sample {

    private static final String ACCESS_KEY_ID;
    private static final String ACCESS_KEY_SECRET;

    static {
        ACCESS_KEY_ID = System.getenv("ACCESS_KEY_ID");
        ACCESS_KEY_SECRET = System.getenv("ACCESS_KEY_SECRET");
    }

    /**
     * <b>description</b> :
     * <p>使用凭据初始化账号Client</p>
     *
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.eds_aic20230930.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(ACCESS_KEY_ID)
                .setAccessKeySecret(ACCESS_KEY_SECRET);
        // Endpoint 请参考 https://api.aliyun.com/product/eds-aic
        config.endpoint = "eds-aic.cn-shanghai.aliyuncs.com";
        return new com.aliyun.eds_aic20230930.Client(config);
    }

    public static List<DescribeAndroidInstancesResponseBody.DescribeAndroidInstancesResponseBodyInstanceModel> getInstances() throws Exception {
        com.aliyun.eds_aic20230930.Client client = Sample.createClient();
        com.aliyun.eds_aic20230930.models.DescribeAndroidInstancesRequest describeAndroidInstancesRequest = new com.aliyun.eds_aic20230930.models.DescribeAndroidInstancesRequest();
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        // 复制代码运行请自行打印 API 的返回值
        DescribeAndroidInstancesResponse resp = client.describeAndroidInstancesWithOptions(describeAndroidInstancesRequest, runtime);
        return resp.getBody().getInstanceModel();
    }

    public static List<BatchGetAcpConnectionTicketResponseBody.BatchGetAcpConnectionTicketResponseBodyInstanceConnectionModels> getTickets(DescribeAndroidInstancesResponseBody.DescribeAndroidInstancesResponseBodyInstanceModel instanceModel) throws Exception {
        com.aliyun.eds_aic20230930.Client client = Sample.createClient();
        com.aliyun.eds_aic20230930.models.BatchGetAcpConnectionTicketRequest batchGetAcpConnectionTicketRequest = new com.aliyun.eds_aic20230930.models.BatchGetAcpConnectionTicketRequest()
                .setInstanceIds(java.util.Arrays.asList(
                        instanceModel.getAndroidInstanceId()
                ))
                .setInstanceGroupId(instanceModel.getAndroidInstanceGroupId());
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        BatchGetAcpConnectionTicketResponse resp = client.batchGetAcpConnectionTicketWithOptions(batchGetAcpConnectionTicketRequest, runtime);
        return resp.getBody().getInstanceConnectionModels();
    }

    public static void disconnect(DescribeAndroidInstancesResponseBody.DescribeAndroidInstancesResponseBodyInstanceModel instanceModel) throws Exception {
        com.aliyun.eds_aic20230930.Client client = Sample.createClient();
        com.aliyun.eds_aic20230930.models.DisconnectAndroidInstanceRequest disconnectAndroidInstanceRequest = new com.aliyun.eds_aic20230930.models.DisconnectAndroidInstanceRequest()
                .setInstanceIds(java.util.Arrays.asList(
                        instanceModel.getAndroidInstanceId()
                ));
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        // 复制代码运行请自行打印 API 的返回值
        DisconnectAndroidInstanceResponse resp = client.disconnectAndroidInstanceWithOptions(disconnectAndroidInstanceRequest, runtime);
        log.info("disconnect resp={}", JSON.toJSONString(resp.getBody()));
    }

    public static String runCommand(DescribeAndroidInstancesResponseBody.DescribeAndroidInstancesResponseBodyInstanceModel instanceModel, String command) throws Exception {
        com.aliyun.eds_aic20230930.Client client = Sample.createClient();
        com.aliyun.eds_aic20230930.models.RunCommandRequest runCommandRequest = new com.aliyun.eds_aic20230930.models.RunCommandRequest()
                .setCommandContent(command)
                .setInstanceIds(java.util.Arrays.asList(
                        instanceModel.getAndroidInstanceId()
                ));
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        RunCommandResponse resp = client.runCommandWithOptions(runCommandRequest, runtime);
        return resp.getBody().getInvokeId();
    }

    public static List<DescribeInvocationsResponseBody.DescribeInvocationsResponseBodyData> getCommandResult(DescribeAndroidInstancesResponseBody.DescribeAndroidInstancesResponseBodyInstanceModel instanceModel, String invokeId) throws Exception {
        com.aliyun.eds_aic20230930.Client client = Sample.createClient();
        com.aliyun.eds_aic20230930.models.DescribeInvocationsRequest describeInvocationsRequest = new com.aliyun.eds_aic20230930.models.DescribeInvocationsRequest()
                .setInvocationId(invokeId)
                .setInstanceIds(java.util.Arrays.asList(
                        instanceModel.getAndroidInstanceId()
                ));
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            // 复制代码运行请自行打印 API 的返回值
        DescribeInvocationsResponse resp = client.describeInvocationsWithOptions(describeInvocationsRequest, runtime);
        return resp.getBody().getData();
    }

    public static void main(String[] args_) throws Exception {
        try {
            List<DescribeAndroidInstancesResponseBody.DescribeAndroidInstancesResponseBodyInstanceModel> instances = getInstances();
            log.info("instances={}", JSON.toJSONString(instances));

            List<BatchGetAcpConnectionTicketResponseBody.BatchGetAcpConnectionTicketResponseBodyInstanceConnectionModels> tickets = getTickets(instances.get(0));
            log.info("tickets={}", JSON.toJSONString(tickets));
//
//            disconnect(instances.get(0));

//            String invokeId = runCommand(instances.get(0), "pm list packages");
//            log.info("invokeId={}", invokeId);

//            List<DescribeInvocationsResponseBody.DescribeInvocationsResponseBodyData> commandResults = getCommandResult(instances.get(0), "t-bj05sa652ju5lvk");
//            log.info("commandResults={}", JSON.toJSONString(commandResults));
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}