import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { JobErrorLog } from '../../types';
import { AuthContext, useMessage } from '../../contexts';
import { JobErrorLogService } from '../../service';
import { Container, FieldValue, FlexBox, Panel } from '../../components';
import { parseShortStringToDateTime } from '../../utils';

const JobErrorLogViewPage: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [jobErrorLog, setJobErrorLog] = useState<JobErrorLog>();

  const auth = useContext(AuthContext);
  const message = useMessage();
  const service = JobErrorLogService();

  useEffect(() => {
    if (id) {
      loadJobErrorLog(id);
    }
  }, [auth.usuario?.token, id]);

  const loadJobErrorLog = async (id: string) => {
    if (!auth.usuario?.token) return;

    try {
      const result = await service.getJobErrorLog(auth.usuario?.token, id);
      if (result) setJobErrorLog(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar o log.', error);
    }
  };

  return (
    <Container>
      <Panel maxWidth='1000px' title='Job Error Log'>
        <FlexBox flexDirection="column">
          <FlexBox flexDirection="row">
            <FlexBox.Item borderRight>
              <FieldValue 
                description='Data'
                type='string'
                value={parseShortStringToDateTime(jobErrorLog?.errorTimestamp)}
              />
            </FlexBox.Item>
            <FlexBox.Item>
              <FieldValue 
                description='Job'
                type='string'
                value={jobErrorLog?.jobName}
              />
            </FlexBox.Item>
          </FlexBox>
          <FlexBox flexDirection="row">
            <FlexBox.Item borderTop>
              <FieldValue 
                description='Descrição'
                type='string'
                value={jobErrorLog?.errorMessage}
              />
            </FlexBox.Item>
          </FlexBox>
          <FlexBox flexDirection="row">
            <FlexBox.Item borderTop>
              <FieldValue 
                description='Stack Trace'
                type='string'
                value={jobErrorLog?.stackTrace}
              />
            </FlexBox.Item>
          </FlexBox>
        </FlexBox>
      </Panel>
    </Container>
  );
};

export default JobErrorLogViewPage;
